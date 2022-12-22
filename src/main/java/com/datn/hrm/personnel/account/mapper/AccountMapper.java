package com.datn.hrm.personnel.account.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.personnel.account.dto.Account;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.personnel.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper implements IMapper<Account, AccountEntity> {

    @Override
    public Account mapDtoFromEntity(AccountEntity entity) {

        Account dto = new Account();

        dto.setId(entity.getId());

        dto.setUsername(entity.getUsername());
//        dto.setPassword(entity.getPassword());
        dto.setFullName(EStatus.ACTIVE.getValue());
        dto.setEmail(entity.getEmail());
        if (ValidatorUtils.isNotNull(entity.getEmployeeEntity())) {
            dto.setEmployee(employeeMapper.mapDtoFromEntity(entity.getEmployeeEntity()));
        }

        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public AccountEntity mapEntityFromDto(AccountEntity entity, Account dto) {

        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setEmployeeEntity(employeeRepository.findById(dto.getEmployee().getId()).get());

        return entity;
    }

    @Override
    public AccountEntity mapEntityFromDto(Account dto) {

        AccountEntity entity = new AccountEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public Page<Account> mapDtoEntityFromEntityPage(Page<AccountEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmployeeService employeeService;
}
