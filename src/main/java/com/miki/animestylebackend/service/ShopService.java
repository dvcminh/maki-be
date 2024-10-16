package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.CreateShopRequest;
import com.miki.animestylebackend.model.Shop;

public interface ShopService {
    public Shop createShop(CreateShopRequest createShopRequest);
    public Shop updateShop();
    public Shop deleteShop();
    public Shop getShop();
}
