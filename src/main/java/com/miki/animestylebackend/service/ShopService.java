package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.model.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface ShopService {
    ShopDto saveShop(User user, ShopSaveDtoRequest shopSaveDtoRequest);
    Shop deleteShop(UUID id);

    PageData<ShopDto> getShop(String name, Boolean isOpen, BigDecimal ratingStart, BigDecimal ratingEnd, CuisineType cuisineType, int page, int size, String sort, String sortBy);

    Shop getShopById(UUID shopId);

    Shop getShopByUserId(UUID id);
}
