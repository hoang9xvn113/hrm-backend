package com.datn.hrm.login.validator;

import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.common.exception.model.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginValidator {

    public void validateForLogin(String username, String password) {

        Optional<AccountEntity> entity = repository.getAccountEntitiesByUsernameAndPassword(username, password);

        if (!entity.isPresent()) {
            throw new UnauthorizedException("Tài khoản hoặc mật khaẩu không đúng");
        }
    }

    @Autowired
    AccountRepository repository;
}
