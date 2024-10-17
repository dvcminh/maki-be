package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.CreateShopRequest;
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
    public ShopDto createShop(CreateShopRequest createShopRequest) {

        return shopMapper.toDto(
                shopRepository.save(Shop.builder()
                        .name(createShopRequest.getName())
                        .description(createShopRequest.getDescription())
                        .address(createShopRequest.getAddress())
                        .email(createShopRequest.getEmail())
                        .phoneNumber(createShopRequest.getPhoneNumber())
                        .rating(BigDecimal.valueOf(0))
                        .imageUrl(createShopRequest.getImageUrl())
                        .build()), "Shop created successfully");

    }

    @Override
    public Shop updateShop() {
        return null;
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
