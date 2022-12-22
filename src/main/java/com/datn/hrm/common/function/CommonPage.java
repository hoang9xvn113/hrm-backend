package com.datn.hrm.common.function;

import com.datn.hrm.common.validator.ValidatorUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommonPage {

    public Pageable getPageable(Integer page, Integer size) {

        page = ValidatorUtils.isNotNull(page) ? page - 1 : 0;

        size = ValidatorUtils.isNotNull(size) ? size : 4;

        return PageRequest.of(page, size);
    }
}
