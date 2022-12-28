package com.datn.hrm.application.absence.controller;


import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.absence.service.ApplicationAbsenceService;
import com.datn.hrm.application.absence.validator.ApplicationAbsenceValidator;
import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ApplicationAbsenceController implements IController<ApplicationAbsence> {

    @Override
    @GetMapping("/application-absences")
    public Page<ApplicationAbsence> getPage(
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

    @GetMapping("/application-absences/{id}")
    @Override
    public ApplicationAbsence getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/application-absences/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForDelete(id);

        service.deleteObject(id);
    }

    @PostMapping("/application-absences")
    @Override
    public ApplicationAbsence postObject(String apiKey, ApplicationAbsence dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/application-absences/{id}")
    @Override
    public ApplicationAbsence putObject(String apiKey, long id, ApplicationAbsence dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @PutMapping("/application-absences/{id}/status")
    public ApplicationAbsence putObjectStatus(
            @RequestHeader("api-key") String apiKey,
            @PathVariable long id,
            @RequestBody ApplicationAbsence dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.putObject(id, dto.getStatus());
    }

    @Autowired
    private ApplicationAbsenceService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ApplicationAbsenceValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
