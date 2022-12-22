package com.datn.hrm.personnel.employee.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    Date birthday;

    @Column
    String sex;

    @Column
    String birthplace;

    @Column
    String address;

    @Column
    String code;

    @Column
    Date handoverDate;

    @Column
    Date startDate;

    @Column
    String handoverPlace;

    @Column
    String martialStatus;

    @Column
    String phoneNumber;

    @Column
    String email;

    @Column
    String description;

    @OneToOne()
    @JoinColumn(name = "contractId", referencedColumnName = "id")
    private ContractEntity contractEntity;

    @Column
    String status;

    @Column
    @LastModifiedDate
    Date modifiedDate;

    @Column
    @CreatedDate
    Date createDate;

    @Column
    long creatorId;

    @PreUpdate
    void preUpdate() {
        this.modifiedDate = new Date();
    }

    @PrePersist
    void prePersist() {
        this.createDate = new Date();
        this.modifiedDate = new Date();
        this.status = EStatus.PENDING.getValue();
    }
}
