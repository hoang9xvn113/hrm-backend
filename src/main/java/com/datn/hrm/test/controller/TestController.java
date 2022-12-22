package com.datn.hrm.test.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.test.dto.Test;
import com.datn.hrm.test.service.TestService;
import com.datn.hrm.test.validator.TestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController implements IController<Test> {

    @Override
    @GetMapping("/tests")
    public Page<Test> getPage(
            String apiKey,
            String search,
            Integer page,
            Integer pageSize,
            String sort,
            String filter) throws Exception {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.getPage(
                search,
                commonPage.getPageable(page, pageSize),
                sort,
                filter);
    }

    @GetMapping("/tests/{id}")
    @Override
    public Test getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/tests/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/tests")
    @Override
    public Test postObject(String apiKey, Test dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping ("/tests/{id}")
    @Override
    public Test putObject(String apiKey, long id, Test dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private TestService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private TestValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
