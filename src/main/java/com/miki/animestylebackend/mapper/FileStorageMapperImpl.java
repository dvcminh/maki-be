package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.model.FileStorage;
import org.springframework.stereotype.Component;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

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
    public List<FileStorageDto> toDto(List<FileStorage> fileStorage) {
        if (fileStorage == null) {
            return null;
        }
        List<FileStorageDto> fileStorageDtoList = new ArrayList<FileStorageDto>();
        for (FileStorage fileStorage1 : fileStorage) {
            fileStorageDtoList.add(toDto(fileStorage1));
        }
        return fileStorageDtoList;
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
