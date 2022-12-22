package com.datn.hrm.setting.personnel.category.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.setting.personnel.category.dto.Category;
import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper implements IMapper<Category, CategoryEntity> {

    @Override
    public Category mapDtoFromEntity(CategoryEntity entity) {

        Category dto = new Category();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public CategoryEntity mapEntityFromDto(Category dto) {

        CategoryEntity entity = new CategoryEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public CategoryEntity mapEntityFromDto(CategoryEntity entity, Category dto) {

        entity.setName(dto.getName().trim());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    @Override
    public Page<Category> mapDtoEntityFromEntityPage(Page<CategoryEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
