package com.datn.hrm.application.leave.mapper;

import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.datn.hrm.setting.application.leave.mapper.LeaveReasonMapper;
import com.datn.hrm.setting.application.leave.repository.LeaveReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ApplicationLeaveMapper implements IMapper<ApplicationLeave, ApplicationLeaveEntity> {

    @Override
    public ApplicationLeave mapDtoFromEntity(ApplicationLeaveEntity entity) {

        ApplicationLeave dto = new ApplicationLeave();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setEmployee(employeeMapper.mapDtoFromEntity(entity.getEmployee()));
        dto.setReason(leaveReasonMapper.mapDtoFromEntity(entity.getReason()));
        dto.setStartShift(entity.getStartShift());
        dto.setStartDate(entity.getStartDate());
        dto.setEndShift(entity.getEndShift());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        dto.setDateCount(entity.getDateCount());

        dto.setStatus(entity.getStatus());
        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public ApplicationLeaveEntity mapEntityFromDto(ApplicationLeave dto) {

        ApplicationLeaveEntity entity = new ApplicationLeaveEntity();

        mapEntityFromDto(entity, dto);

        entity.setEmployee(employeeRepository.getReferenceById(dto.getEmployee().getId()));

        return entity;
    }

    @Override
    public ApplicationLeaveEntity mapEntityFromDto(ApplicationLeaveEntity entity, ApplicationLeave dto) {

//        entity.setName(dto.getName().trim());

        entity.setReason(leaveReasonRepository.getReferenceById(dto.getReason().getId()));
        entity.setStartDate(dto.getStartDate());
        entity.setStartShift(dto.getStartShift());
        entity.setEndDate(dto.getEndDate());
        entity.setEndShift(dto.getEndShift());
        entity.setDescription(dto.getDescription());
        entity.setDateCount(calculateDateCount(dto.getStartShift(), dto.getStartDate(), dto.getEndShift(), dto.getEndDate()));


        return entity;
    }

    public Double calculateDateCount(String startShift, Date startDate, String endShift, Date endDate) {

        double dateCount = 0D;

        LocalDate startLocalDate = LocalDateTime.
                ofInstant(startDate.toInstant(), ZoneId.systemDefault()).
                toLocalDate();
        LocalDate endLocalDate = LocalDateTime.
                ofInstant(endDate.toInstant(), ZoneId.systemDefault()).
                toLocalDate();

        int day = endLocalDate.compareTo(startLocalDate);

        if (startShift.equalsIgnoreCase(endShift)) {

            dateCount += 0.5;
        } else if (startShift.equalsIgnoreCase("first") && endShift.equalsIgnoreCase("second")) {

            dateCount += 1;
        } else if (startShift.equalsIgnoreCase("second") && endShift.equalsIgnoreCase("first")) {

            dateCount -= 0.5;
        }

        dateCount += day;

        return dateCount;
    }

    @Override
    public Page<ApplicationLeave> mapDtoEntityFromEntityPage(Page<ApplicationLeaveEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    LeaveReasonMapper leaveReasonMapper;

    @Autowired
    LeaveReasonRepository leaveReasonRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeRepository employeeRepository;
}
