package com.datn.hrm.setting.application.absence.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AbsenceReason {

    long id;

    String name;

    Integer max;

    String unit;

    Boolean isPaid;

    Boolean isClose;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
