package com.datn.hrm.login.controller;

import com.datn.hrm.personnel.account.service.AccountService;
import com.datn.hrm.login.dto.Token;
import com.datn.hrm.login.dto.User;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.login.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Token login(@RequestBody User user) {

        validator.validateForLogin(user.getUsername(), user.getPassword());

        Token token = new Token();

        token.setAccessToken(provider.generateToken(service.getObject(user.getUsername())));

        return token;
    }

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private AccountService service;

    @Autowired
    private LoginValidator validator;
}
