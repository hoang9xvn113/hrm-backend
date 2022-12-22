package com.datn.hrm.login.provider;

import com.datn.hrm.common.exception.model.UnauthorizedException;
import com.datn.hrm.common.utils.DefaultValue;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.personnel.account.dto.Account;
import com.datn.hrm.personnel.employee.dto.Employee;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "noah";

    private final long JWT_EXPIRATION = 3600000L;

    public String generateToken(Account account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.

        Claims claims = Jwts.claims();

        Employee employee = account.getEmployee();

        claims.put("accountId", account.getId());
        claims.put("username", account.getUsername());
        claims.put("employeeId", ValidatorUtils.isNotNull(account.getEmployee()) ? account.getEmployee().getId() : DefaultValue.LONG);
        if (ValidatorUtils.isNotNull(employee)) {
            claims.put("name", employee.getName());
            claims.put("email", employee.getEmail());
        } else {
            claims.put("name", "admin");
            claims.put("email", "admin@admin.vn");
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public UserModel getUserFromToken(String token) {

        validateToken(token);

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        UserModel userModel = new UserModel();

        userModel.setAccountId(Long.parseLong(claims.get("accountId").toString()));
        userModel.setEmail(claims.get("email").toString());
        userModel.setFullName(claims.get("name").toString());
        userModel.setUsername(claims.get("username").toString());
        userModel.setEmployeeId(Long.parseLong(claims.get("employeeId").toString()));

        return userModel;
    }

    public void validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
        } catch (MalformedJwtException ex) {
            throw new UnauthorizedException("Mã bảo mật không đúng định dạng");
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedException("Mã bảo mật đã hết hạn");
        } catch (UnsupportedJwtException ex) {
            throw new UnauthorizedException("Không hỗ trợ mã bảo mật");
        } catch (IllegalArgumentException ex) {
            throw new UnauthorizedException("Mã bảo mật không được trống");
        }
    }
}