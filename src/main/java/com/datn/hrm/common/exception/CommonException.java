package com.datn.hrm.common.exception;

import com.datn.hrm.common.exception.model.Error;
import com.datn.hrm.common.exception.model.*;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonException {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error unauthorizedException(UnauthorizedException exception) {

        Error error = new Error();

        error.setMessage(exception.getMessage());

        return error;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error entityNotFoundException(EntityNotFoundException exception) {

        Error error = new Error();

        error.setMessage(exception.getMessage());

        return error;
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error entityNotFoundException(DuplicateException exception) {

        Error error = new Error();

        error.setMessage(exception.getMessage());

        return error;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error badRequestException(BadRequestException exception) {

        Error error = new Error();

        error.setMessage(exception.getMessage());

        return error;
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error psqlException(PSQLException exception) {

        Error error = new Error();

        error.setMessage("Không thể xóa bởi đang được sử dụng ở nơi khác");

        return error;
    }


}
