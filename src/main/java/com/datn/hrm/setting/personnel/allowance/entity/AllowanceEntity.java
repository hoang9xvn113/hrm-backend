package com.datn.hrm.setting.personnel.allowance.entity;

import com.datn.hrm.common.utils.EStatus;
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
@Table(name = "allowance")
public class AllowanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    Double money;

    @Column
    String unit;

    @ManyToMany
    @JoinTable(
            name = "contractTypeAllowance",
            joinColumns = @JoinColumn(name = "allowanceId"),
            inverseJoinColumns = @JoinColumn(name = "contractTypeId")
    )
    List<ContractTypeEntity> contractTypeList;

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
