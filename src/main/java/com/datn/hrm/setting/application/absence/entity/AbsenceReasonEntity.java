package com.datn.hrm.setting.application.absence.entity;

import com.datn.hrm.common.utils.EStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
//@EntityListeners(AbsenceReasonListener.class)
@Table(name = "AbsenceReason")
public class AbsenceReasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    Integer max;

    @Column
    String unit;

    @Column
    Boolean isPaid;

    @Column
    Boolean isClose;

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
