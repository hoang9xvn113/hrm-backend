package com.datn.hrm.personnel.employee.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
public class Employee {

    long id;
    
    String name;
    
    Date birthday;
    
    String sex;

    Date modifiedDate;

    String birthplace;

    String address;
    
    String code;
    
    Date handoverDate;

    String handoverPlace;

    Date startDate;

    String position;
    String jobTitle;
    String department;
    
    String martialStatus;
    
    String phoneNumber;
    
    String email;
    
    String description;
    
    String status;

    Date createDate;

    long creatorId;
}
