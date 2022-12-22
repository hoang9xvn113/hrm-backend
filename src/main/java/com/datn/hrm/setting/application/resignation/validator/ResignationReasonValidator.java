package com.datn.hrm.setting.application.resignation.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.application.resignation.dto.ResignationReason;
import com.datn.hrm.setting.application.resignation.entity.ResignationReasonEntity;
import com.datn.hrm.setting.application.resignation.repository.ResignationReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResignationReasonValidator implements IValidator<ResignationReason> {

    @Override
    public void validateForPost(ResignationReason dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, ResignationReason dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<ResignationReasonEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Lý do nghỉ việc không tồn tại");
        }
    }

    private void validateRequiredFields(ResignationReason dto) {

    }

    private void validateExists(ResignationReason dto) {

    }

    private void validateInvalidFields(ResignationReason dto) {

    }

    private void validateDuplicateForAdd(ResignationReason dto) {
        Optional<ResignationReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Lý do nghỉ việc không được trùng");
    }

    private void validateDuplicateForUpdate(long id, ResignationReason dto) {
        Optional<ResignationReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Lý do nghỉ việc không được trùng");
    }

    @Autowired
    ResignationReasonRepository repository;
}
