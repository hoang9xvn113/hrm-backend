package com.datn.hrm.setting.application.resignation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ResignationReason {

    long id;

    String name;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
