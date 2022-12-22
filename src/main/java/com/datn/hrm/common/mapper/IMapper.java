package com.datn.hrm.common.mapper;

import org.springframework.data.domain.Page;

public interface IMapper<T, E> {

    T mapDtoFromEntity(E entity);

    Page<T> mapDtoEntityFromEntityPage(Page<E> entities);

    @Deprecated
    E mapEntityFromDto(T dto);

    E mapEntityFromDto(E entity, T dto);
}
