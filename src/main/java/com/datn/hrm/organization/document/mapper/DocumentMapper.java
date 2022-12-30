package com.datn.hrm.organization.document.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.organization.document.dto.Document;
import com.datn.hrm.organization.document.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class DocumentMapper implements IMapper<Document, DocumentEntity> {

    @Override
    public Document mapDtoFromEntity(DocumentEntity entity) {

        Document dto = new Document();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setType(entity.getType());
        dto.setExtension(entity.getExtension());
        dto.setContentType(entity.getContentType());
        dto.setSize(entity.getSize());
        dto.setParentId(entity.getParentId());
        dto.setTreePath(entity.getTreePath());
        dto.setAppId(entity.getAppId());
        dto.setPkId(entity.getPkId());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public DocumentEntity mapEntityFromDto(Document dto) {

        DocumentEntity entity = new DocumentEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    public DocumentEntity mapEntityFromDto(
            String name,
            String type,
            Long parentId,
            String treePath,
            Long size,
            String contentType,
            String extension,
            Long pkId,
            String appId
    ) {

        DocumentEntity entity = new DocumentEntity();

        entity.setName(name);
        entity.setType(type);
        entity.setParentId(parentId);
        entity.setTreePath(treePath);
        entity.setContentType(contentType);
        entity.setExtension(extension);
        entity.setSize(size);
        entity.setAppId(appId);
        entity.setPkId(pkId);

        return entity;
    }

    @Override
    public DocumentEntity mapEntityFromDto(DocumentEntity entity, Document dto) {

        entity.setName(dto.getName().trim());
        entity.setPkId(dto.getPkId());
        entity.setAppId(dto.getAppId());

        return entity;
    }

    @Override
    public Page<Document> mapDtoEntityFromEntityPage(Page<DocumentEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
