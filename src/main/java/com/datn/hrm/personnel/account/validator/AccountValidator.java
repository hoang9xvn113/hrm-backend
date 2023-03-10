package com.datn.hrm.personnel.account.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.personnel.account.dto.Account;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountValidator implements IValidator<Account> {

    @Override
    public void validateForPost(Account dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Account dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<AccountEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Account không tồn tại");
        }
    }

    private void validateRequiredFields(Account dto) {

    }

    private void validateExists(Account dto) {

    }

    private void validateInvalidFields(Account dto) {

    }

    private void validateDuplicateForAdd(Account dto) {
        Optional<AccountEntity> entity = repository.findByUsername(dto.getUsername().trim());

        if (entity.isPresent()) throw new DuplicateException("Tên đăng nhập không được trùng");

        EmployeeEntity employeeEntity = employeeRepository.getReferenceById(dto.getEmployee().getId());

        if (ValidatorUtils.isNotNull(employeeEntity)) {
            entity = repository.findByEmployeeEntity(employeeEntity);

            if (entity.isPresent()) throw new DuplicateException("Nhân viên này đã được tạo tài khoản");
        }
    }

    private void validateDuplicateForUpdate(long id, Account dto) {
        Optional<AccountEntity> entity = repository.findByUsername(dto.getUsername().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Tên đăng nhập không được trùng");

        EmployeeEntity employeeEntity = employeeRepository.getReferenceById(entity.get().getId());

        if (ValidatorUtils.isNotNull(employeeEntity)) {
            entity = repository.findByEmployeeEntity(employeeEntity);

            if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Nhân viên này đã được tạo tài khoản");
        }
    }

    @Autowired
    AccountRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;
}
