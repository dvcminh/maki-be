package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.ShopDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.mapper.ShopMapper;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    public final ShopMapper shopMapper;

    @Override
    public ShopDto createShop(ShopDtoRequest shopDtoRequest) {

        return shopMapper.toDto(
                shopRepository.save(Shop.builder()
                        .name(shopDtoRequest.getName())
                        .description(shopDtoRequest.getDescription())
                        .address(shopDtoRequest.getAddress())
                        .email(shopDtoRequest.getEmail())
                        .phoneNumber(shopDtoRequest.getPhoneNumber())
                        .rating(BigDecimal.valueOf(0))
                        .imageUrl(shopDtoRequest.getImageUrl())
                        .build()), "Shop created successfully");

    }

    @Override
    public ShopDto updateShop(ShopDtoRequest shopDtoRequest) {
        Shop shop = Shop.builder()
                .name(shopDtoRequest.getName())
                .description(shopDtoRequest.getDescription())
                .address(shopDtoRequest.getAddress())
                .email(shopDtoRequest.getEmail())
                .phoneNumber(shopDtoRequest.getPhoneNumber())
                .rating(BigDecimal.valueOf(0))
                .imageUrl(shopDtoRequest.getImageUrl())
                .build();
        return shopMapper.toDto(shopRepository.save(shop), "Shop updated successfully");
    }

    @Override
    public Shop deleteShop() {
        return null;
    }

    @Override
    public Shop getShop() {
        return null;
    }
}
