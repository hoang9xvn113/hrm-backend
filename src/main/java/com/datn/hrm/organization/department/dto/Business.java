package com.datn.hrm.organization.department.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Business {

    long id;
    
    String name;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
