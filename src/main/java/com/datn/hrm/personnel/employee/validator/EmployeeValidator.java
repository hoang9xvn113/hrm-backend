package com.datn.hrm.personnel.employee.validator;

import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeValidator implements IValidator<Employee> {

    @Override
    public void validateForPost(Employee Employee) {

        validateRequiredFields(Employee);
    }

    @Override
    public void validateForPut(long id, Employee Employee) {

        validateForExist(id);
    }

    @Override
    public void validateForExist(long id) {

        Optional<EmployeeEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Nhân sự không tồn tại");
        }
    }

    private void validateRequiredFields(Employee dto) {

    }

    @Autowired
    EmployeeRepository repository;
}
