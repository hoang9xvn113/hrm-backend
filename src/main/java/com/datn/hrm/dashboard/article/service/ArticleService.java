package com.datn.hrm.dashboard.article.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.dashboard.article.dto.Article;
import com.datn.hrm.dashboard.article.entity.ArticleEntity;
import com.datn.hrm.dashboard.article.mapper.ArticleMapper;
import com.datn.hrm.dashboard.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements IService<Article> {

    @Override
    public Page<Article> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCaseAndCategory(search, filter, pageable)
        );
    }

    @Override
    public Article getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Article postObject(Article dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto)));
    }

    @Override
    public Article putObject(long id, Article dto) {

        ArticleEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ArticleRepository repository;

    @Autowired
    ArticleMapper mapper;
}
