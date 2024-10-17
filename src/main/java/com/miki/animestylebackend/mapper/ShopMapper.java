package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.ShopData;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.Shop;
public interface ShopMapper {
    ShopDto toDto(Shop shop, String message);
    ShopData toShopData(Shop shopDto);
}
