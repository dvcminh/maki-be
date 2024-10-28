package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.page.PaginationResponse;
import com.miki.animestylebackend.dto.request.UpdateRequest;
import com.miki.animestylebackend.dto.response.MultiFileResponse;
import com.miki.animestylebackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileStorageService {
    String deleteFile(UUID fileId, UUID userId);

    String publishFile(UUID fileId, UUID userId);

    String updateFileInfo(UpdateRequest updateRequest, UUID userId);


    List<FileStorageDto> getInfoOfFiles();


    MultiFileResponse getMultiFilesInfo(UUID[] uuids, UUID tenantId);
    MultiFileResponse getMultiFilesInfo(UUID[] uuids);

    PaginationResponse getFilesInfoByUser(UUID userId, int page, int size);

    ResponseEntity<Object> downloadFile(UUID fileId);

    ResponseEntity<Object> downloadFileNoAuth(UUID fileId);

    ResponseEntity<Object> getFile(UUID fileId);

    FileStorageDto saveFile(User user, MultipartFile file, String title);
}
