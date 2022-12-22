package com.datn.hrm.setting.personnel.contract.type.entity;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
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
@Table(name = "contractType")
public class ContractTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    Long categoryId;

    @Column
    Integer duration;

    @Column
    String unit;

    @ManyToMany
    @JoinTable(
            name = "contractTypeAllowance",
            joinColumns = @JoinColumn(name = "contractTypeId"),
            inverseJoinColumns = @JoinColumn(name = "allowanceId")
    )
    List<AllowanceEntity> allowanceEntities;

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
