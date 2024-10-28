package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.Shop;

import java.util.UUID;

public interface ShopService {
    public ShopDto saveShop(ShopSaveDtoRequest shopSaveDtoRequest);
    public Shop deleteShop(UUID id);
    public Shop getShop();
}
