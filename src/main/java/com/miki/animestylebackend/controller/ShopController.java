package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShopController {
    public static ShopService shopService;
    @PostMapping("/register")
    public ResponseEntity<ShopDto> registerShop(@RequestBody ShopSaveDtoRequest shopSaveDtoRequest) {
        return ResponseEntity.ok(shopService.saveShop(shopSaveDtoRequest));
    }

    @PostMapping("/update")
    public ResponseEntity<ShopDto> updateShop(@RequestBody ShopSaveDtoRequest updateShopRequest) {
        return ResponseEntity.ok(shopService.saveShop(updateShopRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable UUID id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok("Shop deleted successfully");
    }
}


