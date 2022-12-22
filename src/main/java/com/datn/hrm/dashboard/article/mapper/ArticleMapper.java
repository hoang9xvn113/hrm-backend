package com.datn.hrm.dashboard.article.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.dashboard.article.dto.Article;
import com.datn.hrm.dashboard.article.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ArticleMapper implements IMapper<Article, ArticleEntity> {

    @Override
    public Article mapDtoFromEntity(ArticleEntity entity) {

        Article dto = new Article();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCategory(entity.getCategory());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public ArticleEntity mapEntityFromDto(Article dto) {

        ArticleEntity entity = new ArticleEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public ArticleEntity mapEntityFromDto(ArticleEntity entity, Article dto) {

        entity.setName(dto.getName().trim());

        entity.setCategory(dto.getCategory());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());

        return entity;
    }

    @Override
    public Page<Article> mapDtoEntityFromEntityPage(Page<ArticleEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
