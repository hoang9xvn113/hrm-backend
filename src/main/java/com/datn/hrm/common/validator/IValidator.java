package com.datn.hrm.common.validator;

public interface IValidator<T> {

    void validateForPost(T dto);

    void validateForPut(long id, T dto);

    void validateForExist(long id);
}
