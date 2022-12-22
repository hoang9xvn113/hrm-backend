package com.datn.hrm.personnel.contract.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.personnel.career.repository.EmployeeCareerRepository;
import com.datn.hrm.personnel.contract.dto.Contract;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.contract.mapper.ContractMapper;
import com.datn.hrm.personnel.contract.repository.ContractRepository;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContractService implements IService<Contract> {

    @Override
    public Page<Contract> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByCodeContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public Contract getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Contract postObject(Contract dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Contract putObject(long id, Contract dto) {

        ContractEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Transactional(
            rollbackOn = {Exception.class}
    )
    public Contract putObject(long id, String status) {

        ContractEntity entity = repository.findById(id).orElseThrow();

        entity.setStatus(status);

        entity = repository.save(entity);

        EmployeeEntity employee = entity.getEmployeeEntity();

        ContractEntity contractEntity = employee.getContractEntity();

        if (ValidatorUtils.isNull(contractEntity) || contractEntity.getEffectiveDate().before(entity.getEffectiveDate())) {
            employee.setContractEntity(entity);

            employeeRepository.save(employee);
        }

        return mapper.mapDtoFromEntity(entity);
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ContractRepository repository;

    @Autowired
    ContractMapper mapper;

    @Autowired
    EmployeeCareerRepository employeeCareerRepository;

    @Autowired
    EmployeeRepository employeeRepository;
}
