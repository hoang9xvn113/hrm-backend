package com.datn.hrm.organization.job.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class JobTitle {

    long id;

    String name;

    String description;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
