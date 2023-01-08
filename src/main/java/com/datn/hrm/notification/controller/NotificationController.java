package com.datn.hrm.notification.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.notification.dto.Notification;
import com.datn.hrm.notification.service.NotificationAccountService;
import com.datn.hrm.notification.service.NotificationService;
import com.datn.hrm.notification.validator.NotificationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController implements IController<Notification> {

    @Override
    @GetMapping("/notifications")
    public Page<Notification> getPage(
            String apiKey,
            String search,
            Integer page,
            Integer pageSize,
            String sort,
            String filter) throws Exception {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.getPage(
                userModel.getEmployeeId(),
                search,
                commonPage.getPageable(page, pageSize),
                sort,
                filter);
    }

    @GetMapping("/notifications/{id}")
    @Override
    public Notification getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/notifications/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/notifications")
    @Override
    public Notification postObject(String apiKey, Notification dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/notifications/{id}")
    @Override
    public Notification putObject(String apiKey, long id, Notification dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);
//
//        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @PutMapping("/notifications/{accountId}/{deviceToken}")
    public void putNotificationAccount(
            @RequestHeader(value = "api-key", required = false) String apiKey,
            @PathVariable Long accountId,
            @PathVariable String deviceToken
    ) {
        notificationAccountService.putNotificationAccount(accountId, deviceToken);
    }

    @DeleteMapping("/notifications/{accountId}/{deviceToken}")
    public void deleteNotificationAccount(
            @RequestHeader(value = "api-key", required = false) String apiKey,
            @PathVariable Long accountId,
            @PathVariable String deviceToken
    ) {
        notificationAccountService.deleteNotificationAccount(accountId, deviceToken);
    }

    @Autowired
    private NotificationService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private NotificationValidator validator;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private NotificationAccountService notificationAccountService;
}
