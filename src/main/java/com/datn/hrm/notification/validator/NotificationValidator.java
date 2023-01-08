package com.datn.hrm.notification.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.notification.dto.Notification;
import com.datn.hrm.notification.entity.NotificationEntity;
import com.datn.hrm.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationValidator implements IValidator<Notification> {

    @Override
    public void validateForPost(Notification dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Notification dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<NotificationEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Notification không tồn tại");
        }
    }

    private void validateRequiredFields(Notification dto) {

    }

    private void validateExists(Notification dto) {

    }

    private void validateInvalidFields(Notification dto) {

    }

    private void validateDuplicateForAdd(Notification dto) {
        Optional<NotificationEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Notification không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Notification dto) {
        Optional<NotificationEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Notification không được trùng");
    }

    @Autowired
    NotificationRepository repository;
}
