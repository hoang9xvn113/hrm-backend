package com.datn.hrm.test.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.test.dto.Test;
import com.datn.hrm.test.entity.TestEntity;
import com.datn.hrm.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestValidator implements IValidator<Test> {

    @Override
    public void validateForPost(Test dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Test dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<TestEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Test không tồn tại");
        }
    }

    private void validateRequiredFields(Test dto) {

    }

    private void validateExists(Test dto) {

    }

    private void validateInvalidFields(Test dto) {

    }

    private void validateDuplicateForAdd(Test dto) {
        Optional<TestEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Test không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Test dto) {
        Optional<TestEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Test không được trùng");
    }

    @Autowired
    TestRepository repository;
}
