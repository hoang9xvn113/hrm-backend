package com.datn.hrm.organization.job.entity;

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
@Table(name = "jobPosition")
public class JobPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    long jobTitleId;

    @Column
    Double maxSalary;

    @Column
    Double minSalary;

    @Column
    String status;

    @Column
    String description;

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
