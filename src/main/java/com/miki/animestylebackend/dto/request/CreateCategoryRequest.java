package com.miki.animestylebackend.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateCategoryRequest {
    private String name;
    private String description;
}
