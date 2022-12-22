package com.datn.hrm.dashboard.article.controller;


import com.datn.hrm.common.controller.IController;
import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.dashboard.article.dto.Article;
import com.datn.hrm.dashboard.article.service.ArticleService;
import com.datn.hrm.dashboard.article.validator.ArticleValidator;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController implements IController<Article> {

    @Override
    @GetMapping("/articles")
    public Page<Article> getPage(
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

    @GetMapping("/articles/{id}")
    @Override
    public Article getObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        return service.getObject(id);
    }

    @DeleteMapping("/articles/{id}")
    @Override
    public void deleteObject(String apiKey, long id) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForExist(id);

        service.deleteObject(id);
    }

    @PostMapping("/articles")
    @Override
    public Article postObject(String apiKey, Article dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

//        validator.validateForPost(dto);

        return service.postObject(dto);
    }

    @PutMapping("/articles/{id}")
    @Override
    public Article putObject(String apiKey, long id, Article dto) {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateForPut(id, dto);

        return service.putObject(id, dto);
    }

    @Autowired
    private ArticleService service;

    @Autowired
    private CommonPage commonPage;

    @Autowired
    private ArticleValidator validator;

    @Autowired
    private JwtTokenProvider provider;
}
