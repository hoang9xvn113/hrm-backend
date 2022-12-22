package com.datn.hrm.personnel.contract.validator;

import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.personnel.contract.dto.Contract;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.contract.repository.ContractRepository;
import com.datn.hrm.setting.personnel.allowance.validator.AllowanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ContractValidator implements IValidator<Contract> {

    @Override
    public void validateForPost(Contract dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Contract dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<ContractEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Hợp đồng không tồn tại");
        }
    }

    private void validateRequiredFields(Contract dto) {

    }

    private void validateExists(Contract dto) {
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

    private void validateInvalidFields(Contract dto) {

    }

    private void validateDuplicateForAdd(Contract dto) {
        Optional<ContractEntity> entity = repository.findByCode(dto.getCode().trim());

        if (entity.isPresent()) throw new DuplicateException("Mã hợp đồng không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Contract dto) {
        Optional<ContractEntity> entity = repository.findByCode(dto.getCode().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Mã hợp đồng không được trùng");
    }

    @Autowired
    ContractRepository repository;
    @Autowired
    AllowanceValidator allowanceValidator;
}
