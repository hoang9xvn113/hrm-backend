package com.datn.hrm.personnel.career.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.personnel.career.dto.EmployeeCareer;
import com.datn.hrm.personnel.career.service.EmployeeCareerService;
import com.datn.hrm.personnel.career.validator.EmployeeCareerValidator;
import com.datn.hrm.personnel.employee.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeCareerController implements IController<EmployeeCareer> {

    @Override
    @GetMapping("/employee-careers")
    public Page<EmployeeCareer> getPage(
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

    @GetMapping("/employee-careers/{id}")
    @Override
    public EmployeeCareer getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/employee-careers/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/employee-careers")
    @Override
    public EmployeeCareer postObject(String apiKey, EmployeeCareer dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/employee-careers/{id}")
    @Override
    public EmployeeCareer putObject(String apiKey, long id, EmployeeCareer dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private EmployeeCareerService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private EmployeeCareerValidator validator;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    EmployeeValidator employeeValidator;
}
