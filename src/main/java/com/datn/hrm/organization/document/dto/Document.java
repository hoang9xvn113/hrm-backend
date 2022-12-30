package com.datn.hrm.organization.document.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Document {

    long id;

    String name;

    Long pkId;

    String appId;

    String type;

    String contentType;

    String extension;

    Long size;

    Long parentId;

    String treePath;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
