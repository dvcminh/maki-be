package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.request.CreateShopRequest;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.service.ShopService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class ShopController {
    public static ShopService shopService;
    @PostMapping("/register")
    public ResponseEntity<Shop> registerShop(CreateShopRequest createShopRequest) {
        return ResponseEntity.ok(shopService.createShop(createShopRequest));
    }
}


