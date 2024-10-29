package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.mapper.ShopMapper;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    public final ShopMapper shopMapper;

    @Override
    public ShopDto saveShop(ShopSaveDtoRequest shopSaveDtoRequest) {
        Shop shop = shopSaveDtoRequest.getId() == null ? new Shop()
                : shopRepository.findById(shopSaveDtoRequest.getId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        BeanUtils.copyProperties(shopSaveDtoRequest, shop);

        shop.setRating(BigDecimal.valueOf(0));
        shop.setRatingCount(0);

        return shopMapper.toDto(shopRepository.save(shop), "Successfully");
    }

    @Override
    public Shop deleteShop(UUID id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shopRepository.delete(shop);
        return shop;
    }

    @Override
    public Shop getShop() {
        return null;
    }
}
