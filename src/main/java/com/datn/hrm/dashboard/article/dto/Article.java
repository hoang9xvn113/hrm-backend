package com.datn.hrm.dashboard.article.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Article {

    long id;

    String name;

    String category;

    String content;

    String thumbnail;

    Date modifiedDate;

    Date createDate;

    long creatorId;
}
