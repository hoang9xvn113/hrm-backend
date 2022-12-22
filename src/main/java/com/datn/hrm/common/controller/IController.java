package com.datn.hrm.common.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/o")
public interface IController<T> {

    Page<T> getPage(@RequestHeader(value = "api-key", required = false) String apiKey,
                    @RequestParam(required = false) String search,
                    @RequestParam(required = false) Integer page,
                    @RequestParam(required = false) Integer size,
                    @RequestParam(required = false) String sort,
                    @RequestParam(required = false) String filter) throws Exception;

    T getObject(@RequestHeader(value = "api-key", required = false) String apiKey,
                @PathVariable long id);

    void deleteObject(@RequestHeader(value = "api-key", required = false) String apiKey,
                      @PathVariable long id);

    T postObject(@RequestHeader(value = "api-key", required = false) String apiKey,
                 @RequestBody T dto);

    T putObject(@RequestHeader(value = "api-key", required = false) String apiKey,
                @PathVariable long id, @RequestBody T dto);
}
