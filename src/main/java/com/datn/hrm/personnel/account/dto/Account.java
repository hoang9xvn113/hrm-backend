package com.datn.hrm.personnel.account.dto;

import com.datn.hrm.personnel.employee.dto.Employee;
import lombok.Data;

import java.util.Date;

@Data
public class Account {

    long id;

    String username;

    String password;

    String email;

    String fullName;

    Employee employee;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
