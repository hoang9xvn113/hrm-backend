package com.datn.hrm.personnel.contract.entity;

import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "contractSalary")
public class ContractSalaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    long contractId;

    @Column
    long categoryId;

    @Column
    double money;
}
