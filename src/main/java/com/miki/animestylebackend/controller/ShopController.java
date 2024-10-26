package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.request.ShopDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopController {
    public static ShopService shopService;
    @PostMapping("/register")
    public ResponseEntity<ShopDto> registerShop(@RequestBody ShopDtoRequest shopDtoRequest) {
        return ResponseEntity.ok(shopService.createShop(shopDtoRequest));
    }

    @PostMapping("/update")
    public ResponseEntity<ShopDto> updateShop(@RequestBody ShopDtoRequest updateShopRequest) {
        return ResponseEntity.ok(shopService.updateShop(updateShopRequest));
    }
}


