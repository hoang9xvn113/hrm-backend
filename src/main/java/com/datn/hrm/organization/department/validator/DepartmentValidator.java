package com.datn.hrm.organization.department.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.utils.GetterUtils;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.department.dto.Department;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentValidator implements IValidator<Department> {

    @Override
    public void validateForPost(Department dto) {

        validateRequiredFields(dto);

        validateExist(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Department dto) {

        validateForExist(id);

        validateExist(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<DepartmentEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Phòng ban không tồn tại");
        }
    }

    private void validateRequiredFields(Department dto) {

    }

    private void validateExist(Department dto) {

        businessValidator.validateForExist(dto.getBusinessId());
        if (ValidatorUtils.isNotNull(dto.getParentId())) {
            validateForExist(dto.getParentId());
        }
    }

    private void validateInvalidFields(Department dto) {

    }

    private void validateChangeParent(long id, Department dto) {

        DepartmentEntity entity = repository.findById(id).get();
    }

    private void validateDuplicateForAdd(Department dto) {

        Optional<DepartmentEntity> nameEntity = repository.findByNameAndParentId(dto.getName().trim(), dto.getParentId());

        if (nameEntity.isPresent()) throw new DuplicateException("Tên phòng ban không được trùng");

        Optional<DepartmentEntity> codeEntity = repository.findByCodeAndParentId(dto.getCode().trim(), dto.getParentId());

        if (codeEntity.isPresent()) throw new DuplicateException("Mã phòng ban không được trùng");
    }

    private void validateDuplicateForUpdate(Long id, Department dto) {

        Optional<DepartmentEntity> nameEntity = repository.findByNameAndParentId(dto.getName().trim(), dto.getParentId());

        if (nameEntity.isPresent() && nameEntity.get().getId() != id)
            throw new DuplicateException("Tên phòng ban không được trùng");

        Optional<DepartmentEntity> codeEntity = repository.findByCodeAndParentId(dto.getCode().trim(), dto.getParentId());

        if (codeEntity.isPresent() && codeEntity.get().getId() != id)
            throw new DuplicateException("Mã phòng ban không được trùng");
    }


    @Autowired
    DepartmentRepository repository;

    @Autowired
    BusinessValidator businessValidator;
}
