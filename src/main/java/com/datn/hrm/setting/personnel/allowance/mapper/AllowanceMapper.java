package com.datn.hrm.setting.personnel.allowance.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AllowanceMapper implements IMapper<Allowance, AllowanceEntity> {

    @Override
    public Allowance mapDtoFromEntity(AllowanceEntity entity) {

        Allowance dto = new Allowance();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setMoney(entity.getMoney());
        dto.setUnit(entity.getUnit());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public AllowanceEntity mapEntityFromDto(Allowance dto) {

        AllowanceEntity entity = new AllowanceEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public AllowanceEntity mapEntityFromDto(AllowanceEntity entity, Allowance dto) {

        entity.setName(dto.getName().trim());
        entity.setMoney(dto.getMoney());
        entity.setUnit(dto.getUnit().trim());

        return entity;
    }

    @Override
    public Page<Allowance> mapDtoEntityFromEntityPage(Page<AllowanceEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
