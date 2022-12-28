package com.datn.hrm.personnel.career.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.personnel.career.dto.EmployeeCareer;
import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import com.datn.hrm.personnel.career.mapper.EmployeeCareerMapper;
import com.datn.hrm.personnel.career.repository.EmployeeCareerRepository;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

@Service
public class EmployeeCareerService implements IService<EmployeeCareer> {

    @Override
    public Page<EmployeeCareer> getPage(String search, Pageable pageable, String sort, String filter) {

        if (ValidatorUtils.isNotNull(filter)) {
            return mapper.mapDtoEntityFromEntityPage(
                    repository.getAllByEmployeeId(Long.parseLong(filter), pageable)
            );
        } else {
            return new PageImpl<>(new ArrayList<>());
        }
    }

    public EmployeeCareerEntity addObject(
            long employeeEntity,
            long departmentEntity,
            long jobPositionEntity,
            long jobTitleEntity,
            long contractTypeEntity,
            Date effectiveDate,
            String status,
            Long pkId
    ) {

        return repository.save(
                mapper.mapEntity(
                        employeeEntity,
                        departmentEntity,
                        jobPositionEntity,
                        jobTitleEntity,
                        contractTypeEntity,
                        effectiveDate,
                        status,
                        pkId
                )
        );
    }

    @Override
    public EmployeeCareer getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public EmployeeCareer postObject(EmployeeCareer dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public EmployeeCareer putObject(long id, EmployeeCareer dto) {

        EmployeeCareerEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    public void deleteObjectByPkId(long pkId) {

        EmployeeCareerEntity entity = repository.getEmployeeCareerEntityByPkId(pkId);

        if (ValidatorUtils.isNotNull(entity)) {

            repository.delete(entity);
        }
    }

    @Autowired
    EmployeeCareerRepository repository;

    @Autowired
    EmployeeCareerMapper mapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeCareerRepository employeeCareerRepository;
}
