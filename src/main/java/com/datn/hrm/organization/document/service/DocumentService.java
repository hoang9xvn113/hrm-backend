package com.datn.hrm.organization.document.service;

import com.datn.hrm.common.utils.DefaultValue;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.document.dto.Document;
import com.datn.hrm.organization.document.entity.DocumentEntity;
import com.datn.hrm.organization.document.mapper.DocumentMapper;
import com.datn.hrm.organization.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    public Page<Document> getPage(Long parentId, String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCaseAndParentId(search, parentId, pageable)
        );
    }

    public Document getObject(Long id) {

        return mapper.mapDtoFromEntity(
                repository.getReferenceById(id)
        );
    }

    public Document rename(Long id, String name) {

        DocumentEntity entity = repository.getReferenceById(id);

        entity.setName(name);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Transactional(rollbackOn = {Exception.class, IOException.class})
    public void deleteDocument(Long id) throws IOException {

        DocumentEntity entity = repository.getReferenceById(id);

        List<DocumentEntity> entityList = repository.findAllByParentId(id);

        if (!entityList.isEmpty()) {

            entityList.forEach(documentEntity -> {

                try {
                    deleteDocument(documentEntity.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Files.deleteIfExists(Path.of(entity.getTreePath()));

        repository.delete(entity);

        decreaseSize(entity.getParentId(), entity.getSize());
    }

    @Transactional(rollbackOn = {Exception.class, IOException.class})
    public void createFolder(Long parentId, String name) throws IOException {

        Path path = getPath(name, parentId);

        repository.save(
                mapper.mapEntityFromDto(
                        name,
                        "folder",
                        parentId,
                        path.toString(),
                        DefaultValue.LONG,
                        null,
                        null
                )
        );

        Files.createDirectory(path);
    }

    public void uploadFile(Long parentId, String action, MultipartFile multipartFile) throws GeneralSecurityException, IOException, URISyntaxException {

        String name = multipartFile.getOriginalFilename();

        String extension = getExtension(name);

        SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy hh_mm_ss");

        if (action.equalsIgnoreCase("hold")) {

            name = name.replace("." + extension, "");

            name = name + " " + sdf.format(new Date()) + "." + extension;
        }

        Path path = getPath(name, parentId);

        if (action.equalsIgnoreCase("edit")) {

            Optional<DocumentEntity> entity = repository.
                    findByNameAndParentId(multipartFile.getOriginalFilename(), parentId);

            if (entity.isPresent()) {

                deleteDocument(entity.get().getId());
            }
        }

        repository.save(mapper.mapEntityFromDto(
                name,
                "file",
                parentId,
                path.toString(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                extension
        ));

        Files.copy(multipartFile.getInputStream(), path);

        increaseSize(parentId, multipartFile.getSize());
    }

    private void increaseSize(Long parentId, Long size) {

        if (ValidatorUtils.isNull(parentId)) return;

        DocumentEntity entity = repository.getReferenceById(parentId);

        entity.setSize(entity.getSize() + size);

        repository.save(entity);

        increaseSize(entity.getParentId(), size);
    }

    private void decreaseSize(Long parentId, Long size) {

        if (ValidatorUtils.isNull(parentId)) return;

        DocumentEntity entity = repository.getReferenceById(parentId);

        if (ValidatorUtils.isNotNull(entity)) {
            entity.setSize(entity.getSize() - size);

            repository.save(entity);

            decreaseSize(entity.getParentId(), size);
        }
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i + 1);
        }

        return extension;
    }

    private Path getPath(String name, Long parentId) {

        String parentPath = "";

        if (ValidatorUtils.isNotNull(parentId)) {

            DocumentEntity entity = repository.getReferenceById(parentId);

            return Paths.get(entity.getTreePath(), name);
        }

        return Paths.get("document", parentPath, name);
    }

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private DocumentMapper mapper;


//    public void uploadFile(String metadata, MultipartFile multipartFile) throws GeneralSecurityException, IOException {
//
//        Drive service = driveCredential.getDrive();
//
//        File fileMetadata = new File();
//
//        fileMetadata.setName(multipartFile.getOriginalFilename());
//
//        FileContent mediaContent = new FileContent(
//                multipartFile.getContentType(),
//                multipartFile.getResource().getFile()
//        );
//
//        try {
//            File file = service.files().create(fileMetadata, mediaContent)
//                    .setFields("id")
//                    .execute();
//            System.out.println("File ID: " + file.getId());
//        } catch (GoogleJsonResponseException e) {
//            // TODO(developer) - handle error appropriately
//            System.err.println("Unable to upload file: " + e.getDetails());
//            throw e;
//        }
//    }

//    @Autowired
//    DriveCredential driveCredential;
}
