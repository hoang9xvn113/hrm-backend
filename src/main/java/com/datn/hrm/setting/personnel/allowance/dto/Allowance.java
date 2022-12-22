package com.datn.hrm.setting.personnel.allowance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Allowance {

    long id;

    String name;

    Double money;

    String unit;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
