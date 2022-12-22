package com.datn.hrm.organization.job.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.organization.job.service.JobTitleService;
import com.datn.hrm.organization.job.validator.JobTitleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobTitleController implements IController<JobTitle> {

    @Override
    @GetMapping("/job-titles")
    public Page<JobTitle> getPage(
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

    @GetMapping("/job-titles/{id}")
    @Override
    public JobTitle getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/job-titles/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/job-titles")
    @Override
    public JobTitle postObject(String apiKey, JobTitle dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping ("/job-titles/{id}")
    @Override
    public JobTitle putObject(String apiKey, long id, JobTitle dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private JobTitleService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private JobTitleValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
