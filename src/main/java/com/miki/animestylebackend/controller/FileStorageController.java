package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.dto.page.PaginationResponse;
import com.miki.animestylebackend.dto.request.UpdateRequest;
import com.miki.animestylebackend.dto.response.MultiFileResponse;
import com.miki.animestylebackend.dto.response.Response;
import com.miki.animestylebackend.model.FileStorage;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/file")
public class FileStorageController extends BaseController {

    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Upload file")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileStorageDto uploadFile(
            @RequestPart(value = "file")
            MultipartFile file,
            @RequestPart(value = "title")
            @Schema(description = "Name of the file")
            String title
    ) {
        User currentUser = getCurrentUser();
        return fileStorageService.saveFile(currentUser, file, title);
    }

    @GetMapping("/getById/{fileId}")
    public FileStorage getFileById(@PathVariable("fileId") UUID fileId) {
        return fileStorageService.findById(fileId);
    }

    @Operation(summary = "Get several files information")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("info")
    public MultiFileResponse getMultipleFiles(@RequestParam("fileId")
                                              @Schema(description = "List of file IDs")
                                              UUID[] uuids) {
        return fileStorageService.getMultiFilesInfo(uuids);
    }

    @Operation(summary = "Get file by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("{fileId}")
    public ResponseEntity<Object> getFile(@PathVariable("fileId") UUID fileId, HttpServletRequest request) {
        return fileStorageService.getFile(fileId);
    }

//    @GetMapping("info")
//    public List<FileStorageDto> getAllFiles() {
//        return fileStorageService.getInfoOfFiles();
//    }

    @Operation(summary = "Get files by user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("info/user")
    public PaginationResponse getFilesInfoByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        User currentUser = getCurrentUser();
        return fileStorageService.getFilesInfoByUser(currentUser.getId(), page, size);
    }

    @Operation(summary = "Download file")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("{fileId}/download")
    public ResponseEntity<Object> downloadFile(@PathVariable("fileId") UUID fileId, HttpServletRequest request) {
        return fileStorageService.downloadFile(fileId);
    }

    @Operation(summary = "Publish/Unpublished file")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{fileId}/publish")
    public Response publishFile(@PathVariable("fileId") UUID fileId) {
        User currentUser = getCurrentUser();
        return new Response(200, fileStorageService.publishFile(fileId,
                currentUser.getId()));
    }

    @Operation(summary = "Update file info")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("info")
    public Response updateFileInfo(@RequestBody UpdateRequest updateRequest, HttpServletRequest request) {
        User currentUser = getCurrentUser();
        return new Response(200, fileStorageService.updateFileInfo(updateRequest,
                currentUser.getId()));
    }

    @Operation(summary = "Delete file by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("{fileId}")
    public Response deleteFile(@PathVariable("fileId") UUID fileId) {
        User currentUser = getCurrentUser();
        return new Response(200, fileStorageService.deleteFile(fileId, currentUser.getId()));
    }
}
