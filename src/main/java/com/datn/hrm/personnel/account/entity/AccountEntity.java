package com.datn.hrm.personnel.account.entity;

import com.datn.hrm.common.utils.DefaultValue;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column
    String email;

    @Column
    String fullName;

    @OneToOne()
    @JoinColumn(name = "employee", referencedColumnName = "id")
    EmployeeEntity employeeEntity;

    @Column
    Date modifiedDate;

    @Column
    Date createDate;

    @Column
    @CreatedBy
    long creatorId = DefaultValue.LONG;

    @PreUpdate
    void preUpdate() {
        this.modifiedDate = new Date();
    }

    @PrePersist
    void prePersist() {
        this.createDate = new Date();
        this.modifiedDate = new Date();
    }
}
