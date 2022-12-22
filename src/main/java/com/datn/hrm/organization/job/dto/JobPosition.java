package com.datn.hrm.organization.job.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class JobPosition {

    long id;

    String name;

    String description;

    Long jobTitleId;

    Double maxSalary;

    Double minSalary;

    JobTitle jobTitle;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
