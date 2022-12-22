package com.datn.hrm.common.utils;

public enum EStatus {

    PENDING("pending"),
    APPROVED("approved"),
    DENIED("denied"),
    DRAFT("draft"),
    EXPIRED("expired"),
    INACTIVE("inactive"),
    ACTIVE("active"),
    INCOMPLETE("incomplete");

    private final String value;

    EStatus(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
