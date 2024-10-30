package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.model.FileStorage;
import org.springframework.stereotype.Component;

@Component
public class FileStorageMapperImpl implements FileStorageMapper {
    @Override
    public FileStorageDto toDto(FileStorage fileStorage) {
        return FileStorageDto.builder()
                .id(fileStorage.getId())
                .title(fileStorage.getTitle())
                .createdBy(fileStorage.getCreatedBy())
                .updatedBy(fileStorage.getUpdatedBy())
                .updatedAt(fileStorage.getUpdatedAt())
                .createdAt(fileStorage.getCreatedAt())
                .extension(fileStorage.getExtension())
                .filePath(fileStorage.getFilePath())
                .isDeleted(fileStorage.getIsDeleted())
                .userId(fileStorage.getUserId())
                .build();
    }

    @Override
    public FileStorage toModel(FileStorageDto fileStorage) {
        return FileStorage.builder()
                .id(fileStorage.getId())
                .title(fileStorage.getTitle())
                .createdBy(fileStorage.getCreatedBy())
                .updatedBy(fileStorage.getUpdatedBy())
                .updatedAt(fileStorage.getUpdatedAt())
                .createdAt(fileStorage.getCreatedAt())
                .extension(fileStorage.getExtension())
                .filePath(fileStorage.getFilePath())
                .isDeleted(fileStorage.getIsDeleted())
                .userId(fileStorage.getUserId())
                .build();
    }
}
