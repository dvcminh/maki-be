package com.miki.animestylebackend.mapper;


import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.model.FileStorage;

public interface FileStorageMapper {
    FileStorageDto mapEntityToDto(FileStorage fileStorage);
}
