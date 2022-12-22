package com.datn.hrm.organization.department.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.organization.department.dto.Business;
import com.datn.hrm.organization.department.entity.BusinessEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BusinessMapper implements IMapper<Business, BusinessEntity> {

    @Override
    public Business mapDtoFromEntity(BusinessEntity entity) {

        Business dto = new Business();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public BusinessEntity mapEntityFromDto(Business dto) {

        BusinessEntity entity = new BusinessEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public BusinessEntity mapEntityFromDto(BusinessEntity entity, Business dto) {

        entity.setName(dto.getName().trim());

        return entity;
    }

    @Override
    public Page<Business> mapDtoEntityFromEntityPage(Page<BusinessEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
