package com.miki.animestylebackend.mapper;


import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.model.FileStorage;

import java.util.List;

public interface FileStorageMapper {
    FileStorageDto toDto(FileStorage fileStorage);
    List<FileStorageDto> toDto(List<FileStorage> fileStorage);
    FileStorage toModel(FileStorageDto fileStorage);

}
