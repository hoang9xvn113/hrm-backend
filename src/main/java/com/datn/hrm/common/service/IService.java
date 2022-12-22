package com.datn.hrm.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {

    Page<T> getPage(
            String search,
            Pageable pageable,
            String sort,
            String filter
    );

    T getObject(long id);

    @Deprecated
    T postObject(T dto);

    T putObject(long id, T dto);

    void deleteObject(long id);
}
