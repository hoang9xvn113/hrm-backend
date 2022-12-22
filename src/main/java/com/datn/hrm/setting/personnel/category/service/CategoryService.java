package com.datn.hrm.setting.personnel.category.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.personnel.category.dto.Category;
import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import com.datn.hrm.setting.personnel.category.mapper.CategoryMapper;
import com.datn.hrm.setting.personnel.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements IService<Category> {

    @Override
    public Page<Category> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCaseAndType(search, filter, pageable)
        );
    }

    public Page<Category> getPage(String search, String type, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCaseAndType(search, type, pageable)
        );
    }

    @Override
    public Category getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Category postObject(Category dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Category putObject(long id, Category dto) {

        CategoryEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryMapper mapper;
}
