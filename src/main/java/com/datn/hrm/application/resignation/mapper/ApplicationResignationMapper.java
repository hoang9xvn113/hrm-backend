package com.datn.hrm.application.resignation.mapper;

import com.datn.hrm.application.resignation.dto.ApplicationResignation;
import com.datn.hrm.application.resignation.entity.ApplicationResignationEntity;
import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.application.resignation.mapper.ResignationReasonMapper;
import com.datn.hrm.setting.application.resignation.repository.ResignationReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ApplicationResignationMapper implements IMapper<ApplicationResignation, ApplicationResignationEntity> {

    @Override
    public ApplicationResignation mapDtoFromEntity(ApplicationResignationEntity entity) {

        ApplicationResignation dto = new ApplicationResignation();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setReviewer(employeeMapper.mapDtoFromEntity(entity.getReviewer()));
        dto.setEmployee(employeeMapper.mapDtoFromEntity(entity.getEmployee()));
        dto.setReason(resignationReasonMapper.mapDtoFromEntity(entity.getReason()));
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());

        dto.setStatus(entity.getStatus());
        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public ApplicationResignationEntity mapEntityFromDto(ApplicationResignation dto) {

        ApplicationResignationEntity entity = new ApplicationResignationEntity();

        mapEntityFromDto(entity, dto);

        entity.setEmployee(employeeRepository.getReferenceById(dto.getEmployee().getId()));

        return entity;
    }

    @Override
    public ApplicationResignationEntity mapEntityFromDto(ApplicationResignationEntity entity, ApplicationResignation dto) {

//        entity.setName(dto.getName().trim());

        entity.setReason(resignationReasonRepository.getReferenceById(dto.getReason().getId()));
        entity.setReviewer(employeeRepository.getReferenceById(dto.getReviewer().getId()));
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());


        return entity;
    }

    private String calculateTimeCount(Date startTime, Date endTime) {

        LocalDateTime startLocalDate = LocalDateTime.
                ofInstant(startTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDate = LocalDateTime.
                ofInstant(endTime.toInstant(), ZoneId.systemDefault());

        LocalDateTime result = endLocalDate
                .minusHours(startLocalDate.getHour())
                .minusMinutes(startLocalDate.getMinute());

        Date resultDate = Date.from(result.atZone(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        return simpleDateFormat.format(resultDate);
    }

    @Override
    public Page<ApplicationResignation> mapDtoEntityFromEntityPage(Page<ApplicationResignationEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    ResignationReasonMapper resignationReasonMapper;

    @Autowired
    ResignationReasonRepository resignationReasonRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeRepository employeeRepository;
}
