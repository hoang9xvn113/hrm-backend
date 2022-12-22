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
    @GeneratedValue
    long id;

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    CategoryEntity categoryEntity;

    @Column
    double money;
}
