package com.datn.hrm.application.resignation.controller;


import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.resignation.dto.ApplicationResignation;
import com.datn.hrm.application.resignation.service.ApplicationResignationService;
import com.datn.hrm.application.resignation.validator.ApplicationResignationValidator;
import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationResignationController implements IController<ApplicationResignation> {

    @Override
    @GetMapping("/application-resignations")
    public Page<ApplicationResignation> getPage(
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

    @GetMapping("/application-resignations/{id}")
    @Override
    public ApplicationResignation getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/application-resignations/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForDelete(id);

        service.deleteObject(id);
    }

    @PostMapping("/application-resignations")
    @Override
    public ApplicationResignation postObject(String apiKey, ApplicationResignation dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

//        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/application-resignations/{id}")
    @Override
    public ApplicationResignation putObject(String apiKey, long id, ApplicationResignation dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);
//
//        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @PutMapping("/application-resignations/{id}/status")
    public ApplicationResignation putObjectStatus(
            @RequestHeader("api-key") String apiKey,
            @PathVariable long id,
            @RequestBody ApplicationResignation dto) throws FirebaseMessagingException {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.putObject(id, userModel.getAccountId(), userModel.getFullName(), dto.getStatus());
    }

    @Autowired
    private ApplicationResignationService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ApplicationResignationValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
