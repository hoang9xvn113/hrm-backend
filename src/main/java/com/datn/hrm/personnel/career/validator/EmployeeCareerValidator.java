package com.datn.hrm.personnel.career.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.personnel.career.dto.EmployeeCareer;
import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import com.datn.hrm.personnel.career.repository.EmployeeCareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeCareerValidator implements IValidator<EmployeeCareer> {

    @Override
    public void validateForPost(EmployeeCareer dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, EmployeeCareer dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<EmployeeCareerEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("EmployeeCareer không tồn tại");
        }
    }

    private void validateRequiredFields(EmployeeCareer dto) {

    }

    private void validateExists(EmployeeCareer dto) {

    }

    private void validateInvalidFields(EmployeeCareer dto) {

    }

    private void validateDuplicateForAdd(EmployeeCareer dto) {

    }

    private void validateDuplicateForUpdate(long id, EmployeeCareer dto) {
    }

    @Autowired
    EmployeeCareerRepository repository;
}
