package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.dto.page.PaginationResponse;
import com.miki.animestylebackend.dto.request.UpdateRequest;
import com.miki.animestylebackend.dto.response.MessageResponse;
import com.miki.animestylebackend.dto.response.MultiFileResponse;
import com.miki.animestylebackend.exception.BadRequestException;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.exception.UnAuthorizedException;
import com.miki.animestylebackend.mapper.FileStorageMapper;
import com.miki.animestylebackend.model.FileStorage;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.jpa.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.storage.path}")
    private String rootPath;

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    private FileStorageMapper fileStorageMapper;

    @Override
    public FileStorageDto saveFile(User user, MultipartFile file, String title) {
        try {
            UUID fileId = UUID.randomUUID();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(fileName);
            String filePath = "/" + fileId;

            Files.createDirectories(Paths.get(rootPath));
            Path targetLocation = Paths.get(rootPath + filePath + "." + extension);
            Files.write(targetLocation, file.getBytes());

            FileStorage fileStorage = FileStorage.builder()
                    .id(fileId)
                    .title(title)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .userId(user.getId())
                    .isDeleted(false)
                    .filePath(filePath)
                    .extension(extension)
                    .build();
            FileStorage savedFile = fileStorageRepository.save(fileStorage);
            return fileStorageMapper.toDto(savedFile);
        } catch (IOException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }


    @Override
    @Transactional
    public String deleteFile(UUID fileId, UUID userId) {
        try {
            FileStorage fileStorage = fileStorageRepository.findById(fileId)
                    .orElseThrow(() -> new NotFoundException("File not found"));
            if (!userId.equals(fileStorage.getUserId()))
                throw new UnAuthorizedException("You are not allow to delete this file");

            if (!checkFileIsStillInMemory(fileStorage)) {
                setDeleted(fileStorage);
                return "Success";
            }
            Path path = Paths.get(rootPath + fileStorage.getFilePath() + "." + fileStorage.getExtension());
            Files.delete(path);
            fileStorage.setIsDeleted(true);
            fileStorageRepository.save(fileStorage);
            return "Success";
        } catch (IOException e) {
            throw new BadRequestException("Failed to delete file");
        }
    }

    @Override
    public String publishFile(UUID fileId, UUID userId) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
        if (fileStorage.getIsDeleted())
            throw new BadRequestException("File is deleted");

        if (!checkFileIsStillInMemory(fileStorage)) {
            setDeleted(fileStorage);
            throw new BadRequestException("File is deleted");
        }

//        fileStorage.setPublished(!fileStorage.isPublished());
        fileStorageRepository.save(fileStorage);
        return "Updated successfully";
    }

    @Override
    public String updateFileInfo(UpdateRequest updateRequest, UUID userId) {
        FileStorage fileStorage = fileStorageRepository.findById(updateRequest.getId()).orElseThrow(() -> new NotFoundException("File not found"));
        if (fileStorage.getIsDeleted())
            throw new BadRequestException("File is deleted");

        if (!checkFileIsStillInMemory(fileStorage)) {
            setDeleted(fileStorage);
            throw new BadRequestException("File is deleted");
        }

        fileStorage.setTitle(updateRequest.getTitle());
        fileStorageRepository.save(fileStorage);
        return "Updated successfully";
    }

    @Override
    public List<FileStorageDto> getInfoOfFiles() {
        return fileStorageRepository.findAll().stream().map(file -> fileStorageMapper.toDto(file)).collect(Collectors.toList());
    }

    @Override
    public MultiFileResponse getMultiFilesInfo(UUID[] uuids) {
        return getMultiFilesInfo(uuids, null);
    }

    @Override
    public PaginationResponse getFilesInfoByUser(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FileStorage> fileStoragePage = fileStorageRepository.findByUserId(pageable, userId);
        List<Object> fileStorageData = fileStoragePage.getContent().stream()
                .map(fileStorage -> getSingleFileInfo(fileStorage, true))
                .collect(Collectors.toList());
        return new PaginationResponse(fileStorageData,
                fileStoragePage.getTotalElements(),
                fileStoragePage.getTotalPages(),
                fileStoragePage.hasNext());
    }

    @Override
    public ResponseEntity<Object> downloadFile(UUID fileId) {
        return getFileResponse(fileId, true);
    }

    @Override
    public ResponseEntity<Object> downloadFileNoAuth(UUID fileId) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
        if (fileStorage.getIsDeleted())
            throw new BadRequestException("File is deleted");

        if (!checkFileIsStillInMemory(fileStorage)) {
            setDeleted(fileStorage);
            throw new BadRequestException("File is deleted");
        }

        try {
            String filePath = fileStorage.getFilePath() + "." + fileStorage.getExtension();
            Path path = Paths.get(rootPath + filePath);
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "application/octet-stream";

            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
            responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileStorage.getTitle() + "." + fileStorage.getExtension() + "\"");

            return responseBuilder.contentType(MediaType.valueOf(contentType)).body(resource);
        } catch (IOException e) {
            throw new BadRequestException("Failed to get file");
        }
    }

    @Override
    public ResponseEntity<Object> getFile(UUID fileId) {
        return getFileResponse(fileId, false);
    }

    private boolean checkFileIsStillInMemory(FileStorage fileStorage) {
        String filePath = fileStorage.getFilePath() + "." + fileStorage.getExtension();
        File file = new File(rootPath + filePath);
        return file.exists();
    }

    private void setDeleted(FileStorage fileStorage) {
        fileStorage.setIsDeleted(true);
        fileStorageRepository.save(fileStorage);
    }

    private Object findAndGetSingleFileInfo(UUID fileId) {
        Optional<FileStorage> foundFile = fileStorageRepository.findById(fileId);
        if (foundFile.isEmpty())
            return new MessageResponse(fileId.toString(), "File is not exists");
        FileStorage fileStorage = foundFile.get();
        return getSingleFileInfo(fileStorage, false);
    }

    public Object getSingleFileInfo(FileStorage fileStorage, boolean isUser) {
        if (fileStorage.getIsDeleted())
            return new MessageResponse(fileStorage.getId().toString(), "File is deleted");
        if (!checkFileIsStillInMemory(fileStorage)) {
            setDeleted(fileStorage);
            return new MessageResponse(fileStorage.getId().toString(), "File is not in memory");
        }
//        if (!fileStorage.isPublished()) {
//            return fileStorageMapper.toDto(fileStorage);
//        }
        if (isUser) {
            return fileStorageMapper.toDto(fileStorage);
        }
        return new MessageResponse(fileStorage.getId().toString(), "File is not publish");
    }

    @Override
    public MultiFileResponse getMultiFilesInfo(UUID[] uuids, UUID tenantId) {
        List<Object> data = Arrays.stream(uuids)
                .map(uuid -> findAndGetSingleFileInfo(uuid, tenantId))
                .collect(Collectors.toList());
        return getMultiFileResponse(data);
    }

    private Object findAndGetSingleFileInfo(UUID fileId, UUID tenantId) {
        Optional<FileStorage> foundFile = fileStorageRepository.findById(fileId);
        if (foundFile.isEmpty())
            return new MessageResponse(fileId.toString(), "File is not exists");
        FileStorage fileStorage = foundFile.get();
        return getSingleFileInfo(fileStorage, false);
    }

    private MultiFileResponse getMultiFileResponse(List<Object> data) {
        List<Object> successData = data.stream().filter(o -> o instanceof FileStorageDto).collect(Collectors.toList());
        int totalElements = successData.size();
        int fail = data.size() - totalElements;
        return new MultiFileResponse(successData, totalElements, fail);
    }

    private ResponseEntity<Object> getFileResponse(UUID fileId, boolean asAttachment) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
        if (fileStorage.getIsDeleted())
            throw new BadRequestException("File is deleted");

        if (!checkFileIsStillInMemory(fileStorage)) {
            setDeleted(fileStorage);
            throw new BadRequestException("File is deleted");
        }

        try {
            String filePath = fileStorage.getFilePath() + "." + fileStorage.getExtension();
            Path path = Paths.get(rootPath + filePath);
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (contentType == null)
                contentType = "application/octet-stream";

            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
            if (asAttachment) {
                responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileStorage.getTitle() + "." + fileStorage.getExtension() + "\"");
            }

            return responseBuilder.contentType(MediaType.valueOf(contentType)).body(resource);
        } catch (IOException e) {
            throw new BadRequestException("Failed to get file");
        }
    }
}

