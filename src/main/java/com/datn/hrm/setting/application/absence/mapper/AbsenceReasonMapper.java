package com.datn.hrm.setting.application.absence.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.setting.application.absence.dto.AbsenceReason;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AbsenceReasonMapper implements IMapper<AbsenceReason, AbsenceReasonEntity> {

    @Override
    public AbsenceReason mapDtoFromEntity(AbsenceReasonEntity entity) {

        AbsenceReason dto = new AbsenceReason();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setMax(entity.getMax());
        dto.setUnit(entity.getUnit());
        dto.setIsPaid(entity.getIsPaid());
        dto.setIsClose(entity.getIsClose());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public AbsenceReasonEntity mapEntityFromDto(AbsenceReason dto) {

        AbsenceReasonEntity entity = new AbsenceReasonEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public AbsenceReasonEntity mapEntityFromDto(AbsenceReasonEntity entity, AbsenceReason dto) {

        entity.setName(dto.getName().trim());

        entity.setMax(dto.getMax());
        entity.setUnit(dto.getUnit());
        entity.setIsPaid(dto.getIsPaid());
        entity.setIsClose(dto.getIsClose());

        return entity;
    }

    @Override
    public Page<AbsenceReason> mapDtoEntityFromEntityPage(Page<AbsenceReasonEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
