package com.datn.hrm.setting.application.resignation.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.setting.application.resignation.dto.ResignationReason;
import com.datn.hrm.setting.application.resignation.entity.ResignationReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ResignationReasonMapper implements IMapper<ResignationReason, ResignationReasonEntity> {

    @Override
    public ResignationReason mapDtoFromEntity(ResignationReasonEntity entity) {

        ResignationReason dto = new ResignationReason();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public ResignationReasonEntity mapEntityFromDto(ResignationReason dto) {

        ResignationReasonEntity entity = new ResignationReasonEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public ResignationReasonEntity mapEntityFromDto(ResignationReasonEntity entity, ResignationReason dto) {

        entity.setName(dto.getName().trim());

        return entity;
    }

    @Override
    public Page<ResignationReason> mapDtoEntityFromEntityPage(Page<ResignationReasonEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
