package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopData;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.model.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface ShopService {
    ShopDto saveShop(User user, ShopSaveDtoRequest shopSaveDtoRequest);
    void deleteShop(UUID id);

    PageData<ShopData> getShop(String name, Boolean isOpen, BigDecimal ratingStart, BigDecimal ratingEnd, CuisineType cuisineType, int page, int size, String sort, String sortBy);

    ShopDto getShopById(UUID shopId);

    ShopDto getShopByUserId(UUID id);
    Map<String, Object> getSalesPerformance(UUID shopId);
    Map<String, Object> getInventoryStatistics(UUID shopId, Integer lowStockThreshold);
    Map<String, Object> getCustomerInsights(UUID shopId);
}
