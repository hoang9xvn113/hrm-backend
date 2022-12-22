package com.datn.hrm.test.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.test.dto.Test;
import com.datn.hrm.test.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TestMapper implements IMapper<Test, TestEntity> {

    @Override
    public Test mapDtoFromEntity(TestEntity entity) {

        Test dto = new Test();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public TestEntity mapEntityFromDto(Test dto) {

        TestEntity entity = new TestEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public TestEntity mapEntityFromDto(TestEntity entity, Test dto) {

        entity.setName(dto.getName().trim());

        return entity;
    }

    @Override
    public Page<Test> mapDtoEntityFromEntityPage(Page<TestEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
