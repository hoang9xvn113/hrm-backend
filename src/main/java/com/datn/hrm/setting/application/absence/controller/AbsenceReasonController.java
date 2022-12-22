package com.datn.hrm.setting.application.absence.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.application.absence.dto.AbsenceReason;
import com.datn.hrm.setting.application.absence.service.AbsenceReasonService;
import com.datn.hrm.setting.application.absence.validator.AbsenceReasonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbsenceReasonController implements IController<AbsenceReason> {

    @Override
    @GetMapping("/absence-reasons")
    public Page<AbsenceReason> getPage(
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

    @GetMapping("/absence-reasons/{id}")
    @Override
    public AbsenceReason getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/absence-reasons/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/absence-reasons")
    @Override
    public AbsenceReason postObject(String apiKey, AbsenceReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/absence-reasons/{id}")
    @Override
    public AbsenceReason putObject(String apiKey, long id, AbsenceReason dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private AbsenceReasonService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private AbsenceReasonValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
