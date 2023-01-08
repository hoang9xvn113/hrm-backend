package com.datn.hrm.application.absence.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
//@EntityListeners(ApplicationAbsenceListener.class)
@Table(name = "ApplicationAbsence")
public class ApplicationAbsenceEntity {

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
    private AbsenceReasonEntity reason;

    @OneToOne()
    @JoinColumn(name = "reviewer", referencedColumnName = "id")
    private EmployeeEntity reviewer;

    @Column
    Date date;

    @Column
    Date startTime;

    @Column
    Date endTime;

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
