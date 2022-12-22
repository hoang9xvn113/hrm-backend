package com.datn.hrm.setting.personnel.contract.type.validator;

import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.setting.personnel.allowance.validator.AllowanceValidator;
import com.datn.hrm.setting.personnel.category.validator.CategoryValidator;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import com.datn.hrm.setting.personnel.contract.type.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ContractTypeValidator implements IValidator<ContractType> {

    @Override
    public void validateForPost(ContractType dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, ContractType dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<ContractTypeEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Loại hợp đồng không tồn tại");
        }
    }

    private void validateRequiredFields(ContractType dto) {

    }

    private void validateExists(ContractType dto) {
        categoryValidator.validateForExist(dto.getCategoryId());

        if (ValidatorUtils.isNotNull(dto.getAllowances())) {

            Set<Long> set = new HashSet<>();
            Arrays.stream(dto.getAllowances()).forEach(allowance -> {
                allowanceValidator.validateForExist(allowance.getId());
                if (!set.add(allowance.getId())) {
                    throw new BadRequestException("Phụ cấp không được trùng");
                }
            });
        }
    }

    private void validateInvalidFields(ContractType dto) {

    }

    private void validateDuplicateForAdd(ContractType dto) {
        Optional<ContractTypeEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Tên loại hợp đồng không được trùng");
    }

    private void validateDuplicateForUpdate(long id, ContractType dto) {
        Optional<ContractTypeEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Tên loại hợp đồng không được trùng");
    }

    @Autowired
    ContractTypeRepository repository;
    @Autowired
    CategoryValidator categoryValidator;
    @Autowired
    AllowanceValidator allowanceValidator;
}
