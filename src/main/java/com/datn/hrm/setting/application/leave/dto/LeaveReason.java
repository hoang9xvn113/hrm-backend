package com.datn.hrm.setting.application.leave.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LeaveReason {

    long id;

    String name;

    Integer max;

    String unit;

    Boolean isPaid;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
