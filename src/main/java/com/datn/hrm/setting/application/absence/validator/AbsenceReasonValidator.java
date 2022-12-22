package com.datn.hrm.setting.application.absence.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.application.absence.dto.AbsenceReason;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import com.datn.hrm.setting.application.absence.repository.AbsenceReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AbsenceReasonValidator implements IValidator<AbsenceReason> {

    @Override
    public void validateForPost(AbsenceReason dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, AbsenceReason dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<AbsenceReasonEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Lý do vắng mặt không tồn tại");
        }
    }

    private void validateRequiredFields(AbsenceReason dto) {

    }

    private void validateExists(AbsenceReason dto) {

    }

    private void validateInvalidFields(AbsenceReason dto) {

    }

    private void validateDuplicateForAdd(AbsenceReason dto) {
        Optional<AbsenceReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Lý do vắng mặt không được trùng");
    }

    private void validateDuplicateForUpdate(long id, AbsenceReason dto) {
        Optional<AbsenceReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Lý do vắng mặt không được trùng");
    }

    @Autowired
    AbsenceReasonRepository repository;
}
