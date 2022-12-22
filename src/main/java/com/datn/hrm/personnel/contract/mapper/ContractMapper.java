package com.datn.hrm.personnel.contract.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.department.mapper.DepartmentMapper;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import com.datn.hrm.organization.job.mapper.JobPositionMapper;
import com.datn.hrm.organization.job.mapper.JobTitleMapper;
import com.datn.hrm.organization.job.repository.JobPositionRepository;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import com.datn.hrm.personnel.contract.dto.Contract;
import com.datn.hrm.personnel.contract.dto.ContractSalary;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.contract.entity.ContractSalaryEntity;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import com.datn.hrm.setting.personnel.allowance.mapper.AllowanceMapper;
import com.datn.hrm.setting.personnel.category.mapper.CategoryMapper;
import com.datn.hrm.setting.personnel.contract.type.mapper.ContractTypeMapper;
import com.datn.hrm.setting.personnel.contract.type.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ContractMapper implements IMapper<Contract, ContractEntity> {

    @Override
    public Contract mapDtoFromEntity(ContractEntity entity) {

        Contract dto = new Contract();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setStatus(entity.getStatus());

        dto.setEmployee(employeeMapper.mapDtoFromEntity(entity.getEmployeeEntity()));
        dto.setContractType(contractTypeMapper.mapDtoFromEntity(entity.getContractTypeEntity()));
        dto.setDepartment(departmentMapper.mapDtoFromEntity(entity.getDepartmentEntity()));
        dto.setJobTitle(jobTitleMapper.mapDtoFromEntity(entity.getJobTitleEntity()));
        dto.setJobPosition(jobPositionMapper.mapDtoFromEntity(entity.getJobPositionEntity()));

        dto.setAllowances(
                entity.getAllowanceEntities().
                        stream().
                        map((allowanceEntity -> allowanceMapper.mapDtoFromEntity(allowanceEntity))).
                        collect(Collectors.toList()).
                        toArray(new Allowance[]{})
        );

//        dto.setContractSalaries(
//                entity.getContractSalaryEntities().
//                        stream().
//                        map((this::mapContractSalaryFromEntity)).
//                        collect(Collectors.toList()).
//                        toArray(new ContractSalary[]{})
//        );

        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setDescription(entity.getDescription());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    public ContractSalary mapContractSalaryFromEntity(ContractSalaryEntity entity) {

        ContractSalary dto = new ContractSalary();

        dto.setCategory(categoryMapper.mapDtoFromEntity(entity.getCategoryEntity()));
        dto.setMoney(entity.getMoney());

        return dto;
    }

    @Override
    public ContractEntity mapEntityFromDto(Contract dto) {

        ContractEntity entity = new ContractEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public ContractEntity mapEntityFromDto(ContractEntity entity, Contract dto) {

//        dto.setName(entity.getName().trim());
        entity.setCode(dto.getCode());

        entity.setEmployeeEntity(employeeRepository.findById(dto.getEmployee().getId()).get());
        entity.setContractTypeEntity(contractTypeRepository.findById(dto.getContractType().getId()).get());
        entity.setDepartmentEntity(departmentRepository.findById(dto.getDepartment().getId()).get());
        entity.setJobPositionEntity(jobPositionRepository.findById(dto.getJobPosition().getId()).get());
        entity.setJobTitleEntity(jobTitleRepository.findById(dto.getJobTitle().getId()).get());

        if (ValidatorUtils.isNotNull(dto.getAllowances())) {
            entity.setAllowanceEntities(
                    Arrays.stream(dto.getAllowances()).map((item) -> {
                        AllowanceEntity allowanceEntity = new AllowanceEntity();

                        allowanceEntity.setId(item.getId());

                        return allowanceEntity;
                    }).collect(Collectors.toList())
            );
        }

        if (ValidatorUtils.isNotNull(dto.getAllowances())) {
            entity.setAllowanceEntities(
                    Arrays.stream(dto.getAllowances()).map((item) -> {
                        AllowanceEntity allowanceEntity = new AllowanceEntity();

                        allowanceEntity.setId(item.getId());

                        return allowanceEntity;
                    }).collect(Collectors.toList())
            );
        }

//        if (ValidatorUtils.isNotNull(dto.getContractSalaries())) {
//            entity.setContractSalaryEntities(
//                    Arrays.stream(dto.getContractSalaries()).map((item) -> {
//
//                        ContractSalaryEntity contractSalaryEntity = new ContractSalaryEntity();
//                        CategoryEntity categoryEntity = new CategoryEntity();
//
//                        categoryEntity.setId(item.getCategory().getId());
//                        contractSalaryEntity.setCategoryEntity(categoryEntity);
//                        contractSalaryEntity.setMoney(item.getMoney());
//                        return contractSalaryEntity;
//                    }).collect(Collectors.toList())
//            );
//        }

        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setDescription(dto.getDescription());

        entity.setEffectiveDate(entity.getEffectiveDate());
        entity.setDescription(entity.getDescription());

        return entity;
    }

    @Override
    public Page<Contract> mapDtoEntityFromEntityPage(Page<ContractEntity> entities) {

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
    @Autowired
    AllowanceMapper allowanceMapper;

    @Autowired
    CategoryMapper categoryMapper;
}
