package com.datn.hrm.personnel.employee.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.repository.JobPositionRepository;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import com.datn.hrm.personnel.career.repository.EmployeeCareerRepository;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        dto.setStatus(entity.getStatus());

        List<EmployeeCareerEntity> employeeCareerEntities = employeeCareerRepository.
                getEmployeeCareers(entity.getId(), new Date());

        if (employeeCareerEntities.size() > 0) {

            EmployeeCareerEntity employeeCareerEntity = employeeCareerEntities.get(0);

            Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(employeeCareerEntity.getDepartmentId());

            departmentEntity.ifPresent(department -> dto.setDepartment(entity.getName()));

            Optional<JobPositionEntity> jobPositionEntity = jobPositionRepository.findById(employeeCareerEntity.getJobPositionId());

            jobPositionEntity.ifPresent(positionEntity -> dto.setPosition(positionEntity.getName()));

            Optional<JobTitleEntity> jobTitleEntity = jobTitleRepository.findById(employeeCareerEntity.getJobTitleId());

            jobTitleEntity.ifPresent(jobTitle -> dto.setJobTitle(jobTitle.getName()));

            dto.setStatus(employeeCareerEntity.getStatus());
        }

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

//    @Autowired
//    EmployeeCareerService employeeCareerService;

    @Autowired
    EmployeeCareerRepository employeeCareerRepository;

    @Autowired
    JobPositionRepository jobPositionRepository;

    @Autowired
    JobTitleRepository jobTitleRepository;

    @Autowired
    DepartmentRepository departmentRepository;
}
