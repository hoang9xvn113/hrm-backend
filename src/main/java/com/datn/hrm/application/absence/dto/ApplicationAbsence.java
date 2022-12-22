package com.datn.hrm.application.absence.dto;

import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.setting.application.absence.dto.AbsenceReason;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApplicationAbsence {

    long id;

    String name;

    Employee employee;

    AbsenceReason reason;

    Date date;

    Date startTime;

    Date endTime;

    String description;

    String timeCount;

    String status;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
