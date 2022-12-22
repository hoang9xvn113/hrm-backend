package com.datn.hrm.personnel.contract.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.personnel.contract.listener.ContractListener;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(ContractListener.class)
@Table(name = "contract")
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    String code;

    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name = "contractType", nullable = false)
    private ContractTypeEntity contractTypeEntity;

    @ManyToOne
    @JoinColumn(name = "jobTitle", nullable = false)
    private JobTitleEntity jobTitleEntity;

    @ManyToOne
    @JoinColumn(name = "jobPosition", nullable = false)
    private JobPositionEntity jobPositionEntity;

    @ManyToOne
    @JoinColumn(name = "department", nullable = false)
    private DepartmentEntity departmentEntity;

    @ManyToMany
    @JoinTable(
            name = "contractAllowance",
            joinColumns = @JoinColumn(name = "contractId"),
            inverseJoinColumns = @JoinColumn(name = "allowanceId")
    )
    List<AllowanceEntity> allowanceEntities;

//    @OneToMany(targetEntity = ContractSalaryEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
//    List<ContractSalaryEntity> contractSalaryEntities;


    @Column
    Date effectiveDate;

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
        this.status = EStatus.PENDING.getValue();
    }
}
