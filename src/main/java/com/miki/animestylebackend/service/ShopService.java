package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;

import java.math.BigDecimal;
import java.util.UUID;

public interface ShopService {
    public ShopDto saveShop(ShopSaveDtoRequest shopSaveDtoRequest);
    public Shop deleteShop(UUID id);

    PageData<ShopDto> getShop(String name, Boolean isOpen, BigDecimal ratingStart, BigDecimal ratingEnd, CuisineType cuisineType, int page, int size, String sort, String sortBy);
}
