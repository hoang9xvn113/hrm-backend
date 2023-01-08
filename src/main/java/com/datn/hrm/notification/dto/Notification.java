package com.datn.hrm.notification.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Notification {

    long id;

    Long pkId;

    String pkName;

    String title;

    String description;

    String name;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
