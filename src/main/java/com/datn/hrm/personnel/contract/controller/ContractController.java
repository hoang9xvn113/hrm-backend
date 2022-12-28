package com.datn.hrm.personnel.contract.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.personnel.contract.dto.Contract;
import com.datn.hrm.personnel.contract.service.ContractService;
import com.datn.hrm.personnel.contract.validator.ContractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController implements IController<Contract> {

    @Override
    @GetMapping("/contracts")
    public Page<Contract> getPage(
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

    @GetMapping("/contracts/{id}")
    @Override
    public Contract getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/contracts/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForDelete(id);

        service.deleteObject(id);
    }

    @PostMapping("/contracts")
    @Override
    public Contract postObject(String apiKey, Contract dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/contracts/{id}")
    @Override
    public Contract putObject(String apiKey, long id, Contract dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @PutMapping("/contracts/{id}/status")
    public Contract putObjectStatus(
            @RequestHeader("api-key") String apiKey,
            @PathVariable long id,
            @RequestBody Contract dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        return service.putObject(id, dto.getStatus());
    }

    @Autowired
    private ContractService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ContractValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
