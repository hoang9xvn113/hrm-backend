package com.datn.hrm.setting.personnel.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Category {

    long id;

    String name;

    String type;

    String description;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
