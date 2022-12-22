package com.datn.hrm.organization.department.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Department {

    long id;
    
    String code;
    
    String name;
    
    long parentId;
    
    long businessId;
    String businessName;

    String address;
    
    String description;

    Date modifiedDate;

    Date createDate;

    long creatorId;

    List<Department> children;
}
