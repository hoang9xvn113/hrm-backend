package com.datn.hrm.personnel.employee.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.service.EmployeeService;
import com.datn.hrm.personnel.employee.validator.EmployeeValidator;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController implements IController<Employee> {

    @Override
    @GetMapping("/employees")
    public Page<Employee> getPage(
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

    @GetMapping("/employees/{id}")
    @Override
    public Employee getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/employees/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/employees")
    @Override
    public Employee postObject(String apiKey, Employee dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/employees/{id}")
    @Override
    public Employee putObject(String apiKey, long id, Employee dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private EmployeeService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private EmployeeValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
