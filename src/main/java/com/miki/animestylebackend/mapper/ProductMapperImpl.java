package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.CategoryData;
import com.miki.animestylebackend.dto.response.ProductDto;
import com.miki.animestylebackend.dto.response.ProductData;
import com.miki.animestylebackend.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final ShopMapper shopMapper;
    @Override
    public ProductDto toProductDto(Product product, String message) {
        if (product == null) {
            return null;
        }

        ProductData productData = toProductData(product);

        ProductDto productDto = new ProductDto();
        productDto.setSuccess(true);
        productDto.setStatus(200);
        productDto.setMessage(message);
        productDto.setData(productData);
        productDto.setTimestamp(LocalDateTime.now());

        return productDto;
    }

    @Override
    public ProductData toProductData(Product product) {
        if (product == null) {
            return null;
        }

        ProductData productData = new ProductData();
        productData.setShopDto(shopMapper.toShopData(product.getShop()));
        productData.setId(product.getId());
        productData.setName(product.getProductName());
        productData.setImage(product.getProductImage());
        productData.setCategoryDto(new CategoryData(product.getCategory().getId(), product.getCategory().getName(), product.getCategory().getDescription()));
        productData.setDescription(product.getProductDescription());
        productData.setPrice(product.getProductPrice());
        productData.setSize(product.getProductSize());
        productData.setQuantity(product.getProductQuantity());

        return productData;
    }
}