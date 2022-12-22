package com.datn.hrm.test.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Test {

    long id;

    String name;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
