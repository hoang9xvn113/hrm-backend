package com.datn.hrm.personnel.contract.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "contractAllowance")
public class ContractAllowanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    long contractId;

    @Column
    long allowanceId;
}
