package com.datn.hrm.personnel.career.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "employeeCareer")
public class EmployeeCareerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    long employeeId;

    @Column
    long contractTypeId;

    @Column
    long jobTitleId;

    @Column
    long jobPositionId;

    @Column
    long departmentId;

    @Column
    Date effectiveDate;

    @Column
    Long pkId;

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
//        this.status = EStatus.PENDING.getValue();
    }
}
