package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.ShopData;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.Shop;
import org.springframework.stereotype.Service;

@Service
public class ShopMapperImpl implements ShopMapper{
    @Override
    public ShopDto toDto(Shop shop, String message) {
        if(shop == null){
            return null;
        }

        ShopData shopData = new ShopData();
        shopData.setId(shop.getId());
        shopData.setName(shop.getName());
        shopData.setAddress(shop.getAddress());
        shopData.setPhoneNumber(shop.getPhoneNumber());
        shopData.setRating(shop.getRating());
        shopData.setRatingCount(shop.getRatingCount());
        shopData.setImageUrl(shop.getImageUrl());
        shopData.setEmail(shop.getEmail());
        shopData.setDescription(shop.getDescription());
        shopData.setOpenTime(shop.getOpenTime());
        shopData.setCloseTime(shop.getCloseTime());
        shopData.setCuisineType(shop.getCuisineType());

        ShopDto shopDto = new ShopDto();
        shopDto.setData(shopData);
        shopDto.setSuccess(true);
        shopDto.setStatus(200);
        shopDto.setMessage(message);

        return shopDto;
    }

    @Override
    public ShopData toShopData(Shop shop) {
        if(shop == null){
            return null;
        }
        ShopData shopData = new ShopData();
        shopData.setId(shop.getId());
        shopData.setName(shop.getName());
        shopData.setAddress(shop.getAddress());
        shopData.setPhoneNumber(shop.getPhoneNumber());
        shopData.setEmail(shop.getEmail());
        shopData.setDescription(shop.getDescription());
        shopData.setOpenTime(shop.getOpenTime());
        shopData.setCloseTime(shop.getCloseTime());
        shopData.setRating(shop.getRating());
        shopData.setRatingCount(shop.getRatingCount());
        shopData.setImageUrl(shop.getImageUrl());
        shopData.setIsOpen(shop.getIsOpen());
        shopData.setCuisineType(shop.getCuisineType());

        return shopData;
    }
}
