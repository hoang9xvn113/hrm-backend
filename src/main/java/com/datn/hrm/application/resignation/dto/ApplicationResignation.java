package com.datn.hrm.application.resignation.dto;

import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.setting.application.resignation.dto.ResignationReason;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApplicationResignation {

    long id;

    String name;

    Employee employee;

    ResignationReason reason;

    Date date;

    String status;

    String description;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
