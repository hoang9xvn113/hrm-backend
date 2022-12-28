package com.datn.hrm.application.resignation.validator;

import com.datn.hrm.application.resignation.dto.ApplicationResignation;
import com.datn.hrm.application.resignation.entity.ApplicationResignationEntity;
import com.datn.hrm.application.resignation.repository.ApplicationResignationRepository;
import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationResignationValidator implements IValidator<ApplicationResignation> {

    @Override
    public void validateForPost(ApplicationResignation dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, ApplicationResignation dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    public void validateForDelete(long id) {

        validateForExist(id);

        ApplicationResignationEntity entity = repository.getReferenceById(id);

        if (entity.getStatus().equalsIgnoreCase(EStatus.APPROVED.getValue())) {
            throw new BadRequestException("Đơn từ này không thể xóa");
        }
    }

    @Override
    public void validateForExist(long id) {

        Optional<ApplicationResignationEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("ApplicationResignation không tồn tại");
        }
    }

    private void validateRequiredFields(ApplicationResignation dto) {

    }

    private void validateExists(ApplicationResignation dto) {

    }

    private void validateInvalidFields(ApplicationResignation dto) {

    }

    private void validateDuplicateForAdd(ApplicationResignation dto) {
        Optional<ApplicationResignationEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("ApplicationResignation không được trùng");
    }

    private void validateDuplicateForUpdate(long id, ApplicationResignation dto) {
        Optional<ApplicationResignationEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("ApplicationResignation không được trùng");
    }

    @Autowired
    ApplicationResignationRepository repository;
}
