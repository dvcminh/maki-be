package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.CategoryDto;
import com.miki.animestylebackend.dto.response.CategoryData;
import com.miki.animestylebackend.dto.response.GetProductGroupByCategoryData;
import com.miki.animestylebackend.dto.response.ProductData;
import com.miki.animestylebackend.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryMapperImpl implements CategoryMapper{
    @Override
    public CategoryDto toCategoryDto(Category category, String message) {
        if (category == null) {
            return null;
        }

        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());
        categoryData.setDescription(category.getDescription());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setSuccess(true);
        categoryDto.setStatus(HttpStatus.OK.value());
        categoryDto.setMessage(message);
        categoryDto.setData(categoryData);
        categoryDto.setTimestamp(LocalDateTime.now());

        return categoryDto;
    }

    @Override
    public CategoryData toCategoryData(Category category) {
        if (category == null) {
            return null;
        }

        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());
        categoryData.setDescription(category.getDescription());

        return categoryData;
    }

    @Override
    public GetProductGroupByCategoryData toGetProductGroupByCategoryData(Category category, List<ProductData> productData) {
        if (category == null) {
            return null;
        }

        GetProductGroupByCategoryData getProductGroupByCategoryData = new GetProductGroupByCategoryData();
        getProductGroupByCategoryData.setCategoryData(toCategoryData(category));
        getProductGroupByCategoryData.setProducts(productData);

        return getProductGroupByCategoryData;
    }
}