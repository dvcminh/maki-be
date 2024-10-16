package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.CategoryData;
import com.miki.animestylebackend.dto.response.CategoryDto;
import com.miki.animestylebackend.dto.response.GetProductGroupByCategoryData;
import com.miki.animestylebackend.dto.response.ProductData;
import com.miki.animestylebackend.model.Category;

import java.util.List;


public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category, String message);

    CategoryData toCategoryData(Category category);
    GetProductGroupByCategoryData toGetProductGroupByCategoryData(Category category, List<ProductData> productData);
}
