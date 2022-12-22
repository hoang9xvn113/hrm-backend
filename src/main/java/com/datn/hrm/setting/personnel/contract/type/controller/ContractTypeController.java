package com.datn.hrm.setting.personnel.contract.type.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import com.datn.hrm.setting.personnel.contract.type.service.ContractTypeService;
import com.datn.hrm.setting.personnel.contract.type.validator.ContractTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractTypeController implements IController<ContractType> {

    @Override
    @GetMapping("/contract-types")
    public Page<ContractType> getPage(
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

    @GetMapping("/contract-types/{id}")
    @Override
    public ContractType getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/contract-types/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/contract-types")
    @Override
    public ContractType postObject(String apiKey, ContractType dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/contract-types/{id}")
    @Override
    public ContractType putObject(String apiKey, long id, ContractType dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private ContractTypeService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ContractTypeValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
