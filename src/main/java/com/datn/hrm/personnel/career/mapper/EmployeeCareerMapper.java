package com.datn.hrm.personnel.career.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.department.mapper.DepartmentMapper;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.mapper.JobPositionMapper;
import com.datn.hrm.organization.job.mapper.JobTitleMapper;
import com.datn.hrm.organization.job.repository.JobPositionRepository;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import com.datn.hrm.personnel.career.dto.EmployeeCareer;
import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import com.datn.hrm.setting.personnel.contract.type.mapper.ContractTypeMapper;
import com.datn.hrm.setting.personnel.contract.type.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeCareerMapper implements IMapper<EmployeeCareer, EmployeeCareerEntity> {

    @Override
    public EmployeeCareer mapDtoFromEntity(EmployeeCareerEntity entity) {

        EmployeeCareer dto = new EmployeeCareer();

        dto.setId(entity.getId());

        entity.setEmployeeEntity(employeeRepository.findById(dto.getEmployee().getId()).get());
        entity.setContractTypeEntity(contractTypeRepository.findById(dto.getContractType().getId()).get());
        entity.setDepartmentEntity(departmentRepository.findById(dto.getDepartment().getId()).get());
        entity.setJobPositionEntity(jobPositionRepository.findById(dto.getJobPosition().getId()).get());
        entity.setJobTitleEntity(jobTitleRepository.findById(dto.getJobTitle().getId()).get());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public EmployeeCareerEntity mapEntityFromDto(EmployeeCareer dto) {

        EmployeeCareerEntity entity = new EmployeeCareerEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    public EmployeeCareerEntity mapEntity(
            EmployeeEntity employeeEntity,
            DepartmentEntity departmentEntity,
            JobPositionEntity jobPositionEntity,
            JobTitleEntity jobTitleEntity,
            ContractTypeEntity contractTypeEntity,
            Date effectiveDate
    ) {
        EmployeeCareerEntity entity = new EmployeeCareerEntity();

        entity.setEmployeeEntity(employeeEntity);
        entity.setDepartmentEntity(departmentEntity);
        entity.setJobPositionEntity(jobPositionEntity);
        entity.setJobTitleEntity(jobTitleEntity);
        entity.setContractTypeEntity(contractTypeEntity);
        entity.setEffectiveDate(effectiveDate);

        return entity;
    }

    @Override
    public EmployeeCareerEntity mapEntityFromDto(EmployeeCareerEntity entity, EmployeeCareer dto) {

        entity.setEmployeeEntity(employeeRepository.findById(dto.getEmployee().getId()).get());
        entity.setDepartmentEntity(departmentRepository.findById(dto.getDepartment().getId()).get());
        entity.setJobPositionEntity(jobPositionRepository.findById(dto.getJobPosition().getId()).get());
        entity.setJobTitleEntity(jobTitleRepository.findById(dto.getJobTitle().getId()).get());
        entity.setContractTypeEntity(contractTypeRepository.findById(dto.getContractType().getId()).get());

        return entity;
    }

    @Override
    public Page<EmployeeCareer> mapDtoEntityFromEntityPage(Page<EmployeeCareerEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    ContractTypeMapper contractTypeMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    JobTitleMapper jobTitleMapper;
    @Autowired
    JobPositionMapper jobPositionMapper;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ContractTypeRepository contractTypeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    JobTitleRepository jobTitleRepository;
    @Autowired
    JobPositionRepository jobPositionRepository;
}
