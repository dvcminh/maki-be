package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.mapper.ShopMapper;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    public final ShopMapper shopMapper;

    @Override
    public ShopDto saveShop(User user, ShopSaveDtoRequest shopSaveDtoRequest) {
        Shop shop = shopSaveDtoRequest.getId() == null ? new Shop()
                : shopRepository.findById(shopSaveDtoRequest.getId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        BeanUtils.copyProperties(shopSaveDtoRequest, shop);

        shop.setUser(user);
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
    public PageData<ShopDto> getShop(String name, Boolean isOpen, BigDecimal ratingStart, BigDecimal ratingEnd, CuisineType cuisineType, int page, int size, String sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), sortBy));
        return new PageData<>(shopRepository.findDistinctByNameContainsAndRatingBetweenAndIsOpenAndCuisineTypeOrderByRatingDescAllIgnoreCase(name, ratingStart, ratingEnd, isOpen, cuisineType, pageable)
                .map(shop -> shopMapper.toDto(shop, "Success")), "Shops found successfully");
    }

    @Override
    public Shop getShopById(UUID shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop not found"));
    }

    @Override
    public Shop getShopByUserId(UUID id) {
        return shopRepository.findByUser_Id(id)
                .orElseThrow(() -> new NotFoundException("Shop not found"));
    }
}
