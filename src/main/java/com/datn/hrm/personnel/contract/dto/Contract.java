package com.datn.hrm.personnel.contract.dto;

import com.datn.hrm.organization.department.dto.Department;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Contract {

    long id;

    String name;

    String code;

    String status;

    Employee employee;

    ContractType contractType;

    JobTitle jobTitle;

    JobPosition jobPosition;

    Department department;

    Allowance[] allowances;

    ContractSalary[] contractSalaries;

    Date effectiveDate;

    String description;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
