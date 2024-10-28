package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.FileStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileStorageRepository extends JpaRepository<FileStorage, UUID> {
    Page<FileStorage> findByUserId(Pageable pageable, UUID userId);

}
