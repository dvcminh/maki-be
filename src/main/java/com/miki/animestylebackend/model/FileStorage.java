package com.miki.animestylebackend.model;

import lombok.Builder;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileStorage {
    @Id
    private UUID id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID userId;
    @Column(
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE"
    )
    private Boolean isDeleted = false;
    private boolean isPublished;
    private String filePath;
    private String extension;
    @ManyToOne
    @JoinColumn(name = "shop_request_id")
    private ShopRequest shopRequest;
}
