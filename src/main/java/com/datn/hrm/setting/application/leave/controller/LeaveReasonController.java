package com.datn.hrm.setting.application.leave.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import com.datn.hrm.setting.application.leave.service.LeaveReasonService;
import com.datn.hrm.setting.application.leave.validator.LeaveReasonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaveReasonController implements IController<LeaveReason> {

    @Override
    @GetMapping("/leave-reasons")
    public Page<LeaveReason> getPage(
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

    @GetMapping("/leave-reasons/{id}")
    @Override
    public LeaveReason getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/leave-reasons/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/leave-reasons")
    @Override
    public LeaveReason postObject(String apiKey, LeaveReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/leave-reasons/{id}")
    @Override
    public LeaveReason putObject(String apiKey, long id, LeaveReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private LeaveReasonService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private LeaveReasonValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
