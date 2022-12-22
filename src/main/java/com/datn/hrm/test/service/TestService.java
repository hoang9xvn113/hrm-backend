package com.datn.hrm.test.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.test.dto.Test;
import com.datn.hrm.test.entity.TestEntity;
import com.datn.hrm.test.mapper.TestMapper;
import com.datn.hrm.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestService implements IService<Test> {

    @Override
    public Page<Test> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public Test getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Test postObject(Test dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Test putObject(long id, Test dto) {

        TestEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    TestRepository repository;

    @Autowired
    TestMapper mapper;
}
