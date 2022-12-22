package com.datn.hrm.setting.personnel.contract.type.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "contractTypeAllowance")
public class ContractTypeAllowanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    long contractTypeId;

    @Column
    long allowanceId;
}
