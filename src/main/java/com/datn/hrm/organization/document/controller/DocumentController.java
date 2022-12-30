package com.datn.hrm.organization.document.controller;

import com.datn.hrm.common.function.CommonPage;
import com.datn.hrm.common.utils.GetterUtils;
import com.datn.hrm.login.dto.UserModel;
import com.datn.hrm.login.provider.JwtTokenProvider;
import com.datn.hrm.organization.document.dto.Document;
import com.datn.hrm.organization.document.service.DocumentService;
import com.datn.hrm.organization.document.validator.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class DocumentController {

    @GetMapping("/o/documents")
    public Page<Document> getPage(
            @RequestHeader(value = "api-key", required = false) String apiKey,
            @RequestParam(value = "pkId", required = true) Long pkId,
            @RequestParam(value = "appId", required = true) String appId,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "filter", required = false) String filter) throws Exception {

        UserModel userModel = provider.getUserFromToken(apiKey);

        validator.validateExist(parentId);

        return service.getPage(
                pkId,
                appId,
                GetterUtils.getLong(parentId),
                search,
                commonPage.getPageable(page, pageSize),
                sort,
                filter);
    }

    @GetMapping("/o/documents/{id}")
    public Document getObject(@PathVariable("id") Long id) throws IOException {

        validator.validateExist(id);

        return service.getObject(id);
    }

    @GetMapping("/documents/{id}/download")
    public ResponseEntity downloadObject(@PathVariable("id") Long id) throws IOException {

        Document dto = service.getObject(id);

        if (dto.getType().equalsIgnoreCase("file")) {
            File file = new File(dto.getTreePath());

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, dto.getContentType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getName() + "\"")
                    .body(resource);
        } else {

            return null;
        }
    }

    @GetMapping("/documents/{id}/preview")
    public ResponseEntity previewObject(@PathVariable("id") Long id) throws FileNotFoundException {

        Document dto = service.getObject(id);

        File file = new File(dto.getTreePath());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, dto.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + dto.getName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/o/documents/{id}")
    public void deleteObject(@PathVariable("id") Long id) throws IOException {

        validator.validateExist(id);

        service.deleteDocument(id);
    }

    @PutMapping(
            value = "/o/documents/{id}/rename"
    )
    public void rename(@PathVariable Long id, @RequestBody Document dto) throws Exception {

        validator.validateExist(id);

        validator.validateDuplicateForUpdate(id, dto);

        service.rename(id, dto.getName());
    }

    @PostMapping(
            value = "/o/create-folder"
    )
    public void createFolder(@RequestBody Document dto) throws Exception {

        validator.validateExist(dto.getParentId());

        validator.validateDuplicateForAdd(dto.getName(), dto.getParentId());

        service.createFolder(dto.getParentId(), dto.getName(), dto.getPkId(), dto.getAppId());
    }

    @PostMapping(
            value = "/o/upload-files",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public void uploadFile(
            @RequestPart("pkId") String pkId,
            @RequestPart("appId") String appId,
            @RequestPart("parentId") String parentId,
            @RequestPart(value = "action") String action,
            @RequestPart("file") MultipartFile multipartFile) throws Exception {

        if (action.equalsIgnoreCase("normal")) {
            validator.validateExist(Long.parseLong(parentId));

            validator.validateDuplicateForAdd(multipartFile.getOriginalFilename(), Long.parseLong(parentId));
        }

        validator.validateSize(multipartFile.getSize());

        service.uploadFile(Long.parseLong(pkId), appId, Long.parseLong(parentId), action, multipartFile);
    }

    @Autowired
    DocumentService service;

    @Autowired
    DocumentValidator validator;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private CommonPage commonPage;
}
