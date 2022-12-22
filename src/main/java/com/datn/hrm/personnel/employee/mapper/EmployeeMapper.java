package com.datn.hrm.personnel.employee.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper implements IMapper<Employee, EmployeeEntity> {

    @Override
    public Employee mapDtoFromEntity(EmployeeEntity entity) {

        Employee dto = new Employee();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setBirthday(entity.getBirthday());
        dto.setSex(entity.getSex());
        dto.setBirthplace(entity.getBirthplace());
        dto.setAddress(entity.getAddress());
        dto.setCode(entity.getCode());
        dto.setHandoverDate(entity.getHandoverDate());
        dto.setMartialStatus(entity.getMartialStatus());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setDescription(entity.getDescription());
        dto.setHandoverPlace(entity.getHandoverPlace());
        dto.setStartDate(entity.getStartDate());

        if (ValidatorUtils.isNotNull(entity.getContractEntity())) {

            dto.setPosition(entity.getContractEntity().getJobPositionEntity().getName());
            dto.setJobTitle(entity.getContractEntity().getJobTitleEntity().getName());
            dto.setDepartment(entity.getContractEntity().getDepartmentEntity().getName());
        }

        dto.setStatus(entity.getStatus());
        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public EmployeeEntity mapEntityFromDto(Employee dto) {

        EmployeeEntity entity = new EmployeeEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public EmployeeEntity mapEntityFromDto(EmployeeEntity entity, Employee dto) {

        entity.setName(dto.getName());
        entity.setBirthday(dto.getBirthday());
        entity.setSex(dto.getSex());
        entity.setBirthplace(dto.getBirthplace());
        entity.setAddress(dto.getAddress());
        entity.setCode(dto.getCode());
        entity.setHandoverDate(dto.getHandoverDate());
        entity.setMartialStatus(dto.getMartialStatus());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setDescription(dto.getDescription());
        entity.setHandoverPlace(dto.getHandoverPlace());
        entity.setStartDate(dto.getStartDate());

        return entity;
    }

    @Override
    public Page<Employee> mapDtoEntityFromEntityPage(Page<EmployeeEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
