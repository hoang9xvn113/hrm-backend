package com.datn.hrm.organization.department.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.organization.department.dto.Business;
import com.datn.hrm.organization.department.entity.BusinessEntity;
import com.datn.hrm.organization.department.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessValidator implements IValidator<Business> {

    @Override
    public void validateForPost(Business dto) {

        validateRequiredFields(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Business dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<BusinessEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Nghiệp vụ không tồn tại");
        }
    }

    private void validateRequiredFields(Business dto) {

    }

    private void validateInvalidFields(Business dto) {


    }

    private void validateDuplicateForAdd(Business dto) {

        Optional<BusinessEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Nghiệp vụ không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Business dto) {

        Optional<BusinessEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Nghiệp vụ không được trùng");
    }

    @Autowired
    BusinessRepository repository;
}
