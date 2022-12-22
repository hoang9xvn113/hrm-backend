package com.datn.hrm.application.leave.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
//@EntityListeners(ApplicationLeaveListener.class)
@Table(name = "ApplicationLeave")
public class ApplicationLeaveEntity {

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
    private LeaveReasonEntity reason;

    @Column
    String startShift;

    @Column
    Date startDate;

    @Column
    String endShift;

    @Column
    Date endDate;

    @Column
    String description;

    @Column
    Double dateCount;

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
