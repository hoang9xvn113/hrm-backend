package com.datn.hrm.login.dto;

import lombok.Data;

@Data
public class UserModel {

    private long accountId;
    private String username;
    private String email;
    private String fullName;
    private long employeeId;
}
