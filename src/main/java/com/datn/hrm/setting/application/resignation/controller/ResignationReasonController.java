package com.datn.hrm.setting.application.resignation.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.application.resignation.dto.ResignationReason;
import com.datn.hrm.setting.application.resignation.service.ResignationReasonService;
import com.datn.hrm.setting.application.resignation.validator.ResignationReasonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResignationReasonController implements IController<ResignationReason> {

    @Override
    @GetMapping("/resignation-reasons")
    public Page<ResignationReason> getPage(
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

    @GetMapping("/resignation-reasons/{id}")
    @Override
    public ResignationReason getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/resignation-reasons/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/resignation-reasons")
    @Override
    public ResignationReason postObject(String apiKey, ResignationReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/resignation-reasons/{id}")
    @Override
    public ResignationReason putObject(String apiKey, long id, ResignationReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private ResignationReasonService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ResignationReasonValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
