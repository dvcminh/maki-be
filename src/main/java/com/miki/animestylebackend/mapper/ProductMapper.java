package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.ProductData;
import com.miki.animestylebackend.dto.response.ProductDto;
import com.miki.animestylebackend.model.Product;

public interface ProductMapper {
    ProductDto toProductDto(Product product, String message);

    ProductData toProductData(Product product);
}
