package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/get")
    public ResponseEntity<PageData<ShopDto>> getShop(@RequestParam(value = "name", defaultValue = "0") String name,
                                                     @RequestParam(value = "isopen", defaultValue = "10") Boolean isOpen,
                                                     @RequestParam(value = "ratingStart", defaultValue = "0") BigDecimal ratingStart,
                                                     @RequestParam(value = "ratingEnd", defaultValue = "0") BigDecimal ratingEnd,
                                                     @RequestParam(value = "cuisineType", defaultValue = "0") CuisineType cuisineType,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort,
                                                     @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy) {
    return ResponseEntity.ok(shopService.getShop(name, isOpen, ratingStart, ratingEnd, cuisineType, page, size, sort, sortBy));
    }



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


