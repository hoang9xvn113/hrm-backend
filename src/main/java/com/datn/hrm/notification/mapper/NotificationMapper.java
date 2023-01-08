package com.datn.hrm.notification.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.notification.dto.Notification;
import com.datn.hrm.notification.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper implements IMapper<Notification, NotificationEntity> {

    @Override
    public Notification mapDtoFromEntity(NotificationEntity entity) {

        Notification dto = new Notification();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setPkId(entity.getPkId());
        dto.setDescription(entity.getDescription());
        dto.setPkName(entity.getPkName());
        dto.setTitle(entity.getTitle());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public NotificationEntity mapEntityFromDto(Notification dto) {

        NotificationEntity entity = new NotificationEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public NotificationEntity mapEntityFromDto(NotificationEntity entity, Notification dto) {

        entity.setName(dto.getName().trim());

        return entity;
    }

    @Override
    public Page<Notification> mapDtoEntityFromEntityPage(Page<NotificationEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
