package com.datn.hrm.setting.personnel.contract.type.dto;

import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ContractType {

    long id;

    String name;

    Long categoryId;

    String categoryName;

    Integer duration;

    String unit;

    Long[] allowanceIds;

    Allowance[] allowances;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
