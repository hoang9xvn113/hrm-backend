package com.datn.hrm.application.leave.controller;


import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.leave.service.ApplicationLeaveService;
import com.datn.hrm.application.leave.validator.ApplicationLeaveValidator;
import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationLeaveController implements IController<ApplicationLeave> {

    @Override
    @GetMapping("/application-leaves")
    public Page<ApplicationLeave> getPage(
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

    @GetMapping("/application-leaves/{id}")
    @Override
    public ApplicationLeave getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/application-leaves/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForDelete(id);

        service.deleteObject(id);
    }

    @PostMapping("/application-leaves")
    @Override
    public ApplicationLeave postObject(String apiKey, ApplicationLeave dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/application-leaves/{id}")
    @Override
    public ApplicationLeave putObject(String apiKey, long id, ApplicationLeave dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @PutMapping("/application-leaves/{id}/status")
    public ApplicationLeave putObjectStatus(
            @RequestHeader("api-key") String apiKey,
            @PathVariable long id,
            @RequestBody ApplicationLeave dto) throws FirebaseMessagingException {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.putObject(id, userModel.getAccountId(), userModel.getFullName(), dto.getStatus());
    }

    @Autowired
    private ApplicationLeaveService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ApplicationLeaveValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
