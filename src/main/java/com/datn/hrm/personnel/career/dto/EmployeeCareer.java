package com.datn.hrm.personnel.career.dto;

import com.datn.hrm.organization.department.dto.Department;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeCareer {

    long id;

    Employee employee;

    ContractType contractType;

    JobTitle jobTitle;

    JobPosition jobPosition;

    Department department;

    String status;

    Date effectiveDate;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
