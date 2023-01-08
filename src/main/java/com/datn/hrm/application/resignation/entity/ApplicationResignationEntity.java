package com.datn.hrm.application.resignation.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.application.resignation.entity.ResignationReasonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
//@EntityListeners(ApplicationResignationListener.class)
@Table(name = "ApplicationResignation")
public class ApplicationResignationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @OneToOne()
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private EmployeeEntity employee;

    @OneToOne()
    @JoinColumn(name = "reasonId", referencedColumnName = "id")
    private ResignationReasonEntity reason;

    @OneToOne()
    @JoinColumn(name = "reviewer", referencedColumnName = "id")
    private EmployeeEntity reviewer;

    @Column
    Date date;

    @Column
    String description;

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
        this.status = EStatus.DRAFT.getValue();
    }
}
