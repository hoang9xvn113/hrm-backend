package com.datn.hrm.login.filter;

import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.login.provider.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountRepository repository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.startsWith("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);
//        // Lấy jwt từ request
//        String jwt = getJwtFromRequest(request);
//
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//            // Lấy id user từ chuỗi jwt
//            UserModel user = tokenProvider.getUserFromToken(jwt);
//            // Lấy thông tin người dùng từ id
//
//            Optional<AccountEntity> entity = repository.findById(user.getAccountId());
//
//            if (entity.isPresent()) {
//                // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
//                UsernamePasswordAuthenticationToken
//                        authentication = new UsernamePasswordAuthenticationToken(entity, null);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//            filterChain.doFilter(request, response);
//        } else {
//            throw new IOException("Lỗi xác thực tài khoản");
//        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}