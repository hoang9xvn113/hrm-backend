package com.datn.hrm.setting.personnel.category.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.setting.personnel.category.dto.Category;
import com.datn.hrm.setting.personnel.category.service.CategoryService;
import com.datn.hrm.setting.personnel.category.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController implements IController<Category> {

    @Override
    @GetMapping("/categories")
    public Page<Category> getPage(
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

    @GetMapping("/categories/{id}")
    @Override
    public Category getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/categories/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/categories")
    @Override
    public Category postObject(String apiKey, Category dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping ("/categories/{id}")
    @Override
    public Category putObject(String apiKey, long id, Category dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private CategoryService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private CategoryValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
