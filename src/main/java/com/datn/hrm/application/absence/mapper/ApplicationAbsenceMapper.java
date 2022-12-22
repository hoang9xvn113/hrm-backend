package com.datn.hrm.application.absence.mapper;

import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.absence.entity.ApplicationAbsenceEntity;
import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.application.absence.mapper.AbsenceReasonMapper;
import com.datn.hrm.setting.application.absence.repository.AbsenceReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ApplicationAbsenceMapper implements IMapper<ApplicationAbsence, ApplicationAbsenceEntity> {

    @Override
    public ApplicationAbsence mapDtoFromEntity(ApplicationAbsenceEntity entity) {

        ApplicationAbsence dto = new ApplicationAbsence();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setEmployee(employeeMapper.mapDtoFromEntity(entity.getEmployee()));
        dto.setReason(absenceReasonMapper.mapDtoFromEntity(entity.getReason()));
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setDescription(entity.getDescription());

        dto.setStatus(entity.getStatus());
        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setTimeCount(calculateTimeCount(entity.getStartTime(), entity.getEndTime()));

        return dto;
    }

    @Override
    public ApplicationAbsenceEntity mapEntityFromDto(ApplicationAbsence dto) {

        ApplicationAbsenceEntity entity = new ApplicationAbsenceEntity();

        mapEntityFromDto(entity, dto);

        entity.setEmployee(employeeRepository.getReferenceById(dto.getEmployee().getId()));

        return entity;
    }

    @Override
    public ApplicationAbsenceEntity mapEntityFromDto(ApplicationAbsenceEntity entity, ApplicationAbsence dto) {

//        entity.setName(dto.getName().trim());

        entity.setReason(absenceReasonRepository.getReferenceById(dto.getReason().getId()));
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDescription(dto.getDescription());


        return entity;
    }

    public String calculateTimeCount(Date startTime, Date endTime) {

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
    public Page<ApplicationAbsence> mapDtoEntityFromEntityPage(Page<ApplicationAbsenceEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    AbsenceReasonMapper absenceReasonMapper;

    @Autowired
    AbsenceReasonRepository absenceReasonRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeRepository employeeRepository;
}
