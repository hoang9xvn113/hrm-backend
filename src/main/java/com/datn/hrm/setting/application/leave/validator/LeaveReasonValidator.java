package com.datn.hrm.setting.application.leave.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import com.datn.hrm.setting.application.leave.repository.LeaveReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaveReasonValidator implements IValidator<LeaveReason> {

    @Override
    public void validateForPost(LeaveReason dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, LeaveReason dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<LeaveReasonEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Lý do xin nghỉ không tồn tại");
        }
    }

    private void validateRequiredFields(LeaveReason dto) {

    }

    private void validateExists(LeaveReason dto) {

    }

    private void validateInvalidFields(LeaveReason dto) {

    }

    private void validateDuplicateForAdd(LeaveReason dto) {
        Optional<LeaveReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Lý do xin nghỉ không được trùng");
    }

    private void validateDuplicateForUpdate(long id, LeaveReason dto) {
        Optional<LeaveReasonEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Lý do xin nghỉ không được trùng");
    }

    @Autowired
    LeaveReasonRepository repository;
}
