package com.datn.hrm.common.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateException extends RuntimeException{

    String message;
}
