package com.datn.hrm.organization.job.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.service.JobPositionService;
import com.datn.hrm.organization.job.validator.JobPositionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobPositionController implements IController<JobPosition> {

    @Override
    @GetMapping("/job-positions")
    public Page<JobPosition> getPage(
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

    @GetMapping("/job-positions/{id}")
    @Override
    public JobPosition getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/job-positions/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/job-positions")
    @Override
    public JobPosition postObject(String apiKey, JobPosition dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/job-positions/{id}")
    @Override
    public JobPosition putObject(String apiKey, long id, JobPosition dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private JobPositionService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private JobPositionValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
