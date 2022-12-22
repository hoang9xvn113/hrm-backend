package com.datn.hrm.setting.personnel.allowance.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.service.AllowanceService;
import com.datn.hrm.setting.personnel.allowance.validator.AllowanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class AllowanceController implements IController<Allowance> {

    @Override
    @GetMapping("/allowances")
    public Page<Allowance> getPage(
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

    @GetMapping("/allowances/{id}")
    @Override
    public Allowance getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/allowances/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/allowances")
    @Override
    public Allowance postObject(String apiKey, Allowance dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping ("/allowances/{id}")
    @Override
    public Allowance putObject(String apiKey, long id, Allowance dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private AllowanceService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private AllowanceValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
