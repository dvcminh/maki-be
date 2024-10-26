package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.ShopDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.Shop;

public interface ShopService {
    public ShopDto createShop(ShopDtoRequest shopDtoRequest);
    public ShopDto updateShop(ShopDtoRequest updateShopRequest);
    public Shop deleteShop();
    public Shop getShop();
}
