package com.datn.hrm.personnel.contract.dto;

import com.datn.hrm.setting.personnel.category.dto.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractSalary {

    long id;

    Category category;

    double money;
}
