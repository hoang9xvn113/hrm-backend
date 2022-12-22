package com.datn.hrm.application.leave.dto;

import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApplicationLeave {

    long id;

    String name;

    Employee employee;

    LeaveReason reason;

    String startShift;

    Date startDate;

    String endShift;

    Date endDate;

    String description;

    Double dateCount;

    String status;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
