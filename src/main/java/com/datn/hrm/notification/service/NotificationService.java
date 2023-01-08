package com.datn.hrm.notification.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.notification.dto.Notification;
import com.datn.hrm.notification.entity.NotificationAccountEntity;
import com.datn.hrm.notification.entity.NotificationEntity;
import com.datn.hrm.notification.mapper.NotificationMapper;
import com.datn.hrm.notification.repository.NotificationAccountRepository;
import com.datn.hrm.notification.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements IService<Notification> {

    @Override
    public Page<Notification> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.findAll(pageable)
        );
    }

    public Page<Notification> getPage(Long employeeId, String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByReceiverIdOrderByCreateDateDesc(employeeId, pageable)
        );
    }

    @Override
    public Notification getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Notification postObject(Notification dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Notification putObject(long id, Notification dto) {

        NotificationEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    public NotificationEntity addNotification(
            long pkId,
            String pkName,
            String title,
            String description,
            long creatorId,
            long receiverId
    ) throws FirebaseMessagingException {

        NotificationEntity entity = new NotificationEntity();

        entity.setPkId(pkId);
        entity.setPkName(pkName);
        entity.setDescription(description);
        entity.setCreatorId(creatorId);
        entity.setReceiverId(receiverId);
        entity.setTitle(title);

        entity = repository.save(entity);

        List<String> tokens = notificationAccountService.getTokens(receiverId);

        notificationAccountService.sendMessage(
                pkId,
                pkName,
                title,
                description,
                tokens
        );

        return entity;
    }

    @Autowired
    NotificationRepository repository;

    @Autowired
    NotificationMapper mapper;

    @Autowired
    NotificationAccountService notificationAccountService;
}
