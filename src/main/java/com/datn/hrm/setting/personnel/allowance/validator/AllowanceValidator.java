package com.datn.hrm.setting.personnel.allowance.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import com.datn.hrm.setting.personnel.allowance.repository.AllowanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllowanceValidator implements IValidator<Allowance> {

    @Override
    public void validateForPost(Allowance dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Allowance dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<AllowanceEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Phụ cấp không tồn tại");
        }
    }

    private void validateRequiredFields(Allowance dto) {

    }

    private void validateExists(Allowance dto) {

    }

    private void validateInvalidFields(Allowance dto) {

    }

    private void validateDuplicateForAdd(Allowance dto) {
        Optional<AllowanceEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Phụ cấp không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Allowance dto) {
        Optional<AllowanceEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Phụ cấp không được trùng");
    }

    @Autowired
    AllowanceRepository repository;
}
