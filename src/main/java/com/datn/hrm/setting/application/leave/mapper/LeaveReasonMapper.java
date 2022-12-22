package com.datn.hrm.setting.application.leave.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LeaveReasonMapper implements IMapper<LeaveReason, LeaveReasonEntity> {

    @Override
    public LeaveReason mapDtoFromEntity(LeaveReasonEntity entity) {

        LeaveReason dto = new LeaveReason();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setMax(entity.getMax());
        dto.setUnit(entity.getUnit());
        dto.setIsPaid(entity.getIsPaid());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public LeaveReasonEntity mapEntityFromDto(LeaveReason dto) {

        LeaveReasonEntity entity = new LeaveReasonEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public LeaveReasonEntity mapEntityFromDto(LeaveReasonEntity entity, LeaveReason dto) {

        entity.setName(dto.getName().trim());

        entity.setMax(dto.getMax());
        entity.setUnit(dto.getUnit());
        entity.setIsPaid(dto.getIsPaid());

        return entity;
    }

    @Override
    public Page<LeaveReason> mapDtoEntityFromEntityPage(Page<LeaveReasonEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
