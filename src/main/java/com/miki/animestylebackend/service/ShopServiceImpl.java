package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.ShopSaveDtoRequest;
import com.miki.animestylebackend.dto.response.ProductData;
import com.miki.animestylebackend.dto.response.ShopData;
import com.miki.animestylebackend.dto.response.ShopDto;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.mapper.ShopMapper;
import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.model.Shop;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.jpa.OrderRepository;
import com.miki.animestylebackend.repository.jpa.ProductRepository;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import com.miki.animestylebackend.repository.jpa.ShopReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ShopReviewsRepository shopReviewsRepository;
    private final ProductMapper productMapper;

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
    public void deleteShop(UUID id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shopRepository.delete(shop);
    }

    @Override
    public PageData<ShopData> getShop(String name, Boolean isOpen, BigDecimal ratingStart, BigDecimal ratingEnd, CuisineType cuisineType, int page, int size, String sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), sortBy));
        if (name.isEmpty()) {
            return new PageData<>(shopRepository.findAll(pageable)
                    .map(shopMapper::toShopData), "Shops found successfully");
        }
        return new PageData<>(shopRepository.findDistinctByNameContainsAndRatingBetweenAndIsOpenAndCuisineTypeOrderByRatingDescAllIgnoreCase(name, ratingStart, ratingEnd, isOpen, cuisineType, pageable)
                .map(shopMapper::toShopData), "Shops found successfully");
    }

    @Override
    public ShopDto getShopById(UUID shopId) {
        return shopMapper.toDto(shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop not found")), "Shop found successfully");
    }

    @Override
    public ShopDto getShopByUserId(UUID id) {
        return shopMapper.toDto(shopRepository.findByUser_Id(id)
                .orElseThrow(() -> new NotFoundException("Shop not found")), "Shop found successfully");
    }

    @Override
    public Map<String, Object> getSalesPerformance(UUID shopId) {
        BigDecimal totalRevenue = orderRepository.findTotalRevenueByShopId(shopId);
        Long orderCount = orderRepository.findOrderCountByShopId(shopId);
        BigDecimal averageOrderValue = orderRepository.findAverageOrderValueByShopId(shopId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", totalRevenue);
        stats.put("orderCount", orderCount);
        stats.put("averageOrderValue", averageOrderValue);
        return stats;
    }

    public List<Map<String, Object>> getTopSellingProducts(UUID shopId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = productRepository.findTopSellingProductsByShopId(shopId, pageable);

        List<Map<String, Object>> topSellingProducts = new ArrayList<>();
        for (Object[] result : results) {
            Product product = (Product) result[0];
            Long totalSold = ((Number) result[1]).longValue();

            Map<String, Object> productData = new HashMap<>();
            productData.put("productName", product.getProductName());
            productData.put("totalSold", totalSold);
            productData.put("price", product.getProductPrice());
            productData.put("productDescription", product.getProductDescription());
            topSellingProducts.add(productData);
        }

        return topSellingProducts;
    }


    public List<Map<String, Object>> getLowStockProducts(UUID shopId, int threshold) {
        List<Object[]> results = productRepository.findLowStockProductsByShopId(shopId, threshold);

        List<Map<String, Object>> lowStockProducts = new ArrayList<>();
        for (Object[] result : results) {
            String productName = (String) result[0];
            Integer productQuantity = (Integer) result[1];
            BigDecimal productPrice = (BigDecimal) result[2];

            Map<String, Object> productData = new HashMap<>();
            productData.put("productName", productName);
            productData.put("quantity", productQuantity);
            productData.put("price", productPrice);
            lowStockProducts.add(productData);
        }

        return lowStockProducts;
    }

    @Override
    public Map<String, Object> getCustomerInsights(UUID shopId) {
        Long distinctCustomerCount = shopReviewsRepository.findDistinctCustomerCountByShopId(shopId);
        BigDecimal averageRating = shopReviewsRepository.findAverageRatingByShopId(shopId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("distinctCustomerCount", distinctCustomerCount);
        stats.put("averageRating", averageRating);
        return stats;
    }

    @Override
    public Map<String, Object> getInventoryStatistics(UUID shopId, Integer lowStockThreshold) {
        List<Map<String, Object>> lowStockProducts = getLowStockProducts(shopId, lowStockThreshold);
        List<Map<String, Object>> topSellingProducts = getTopSellingProducts(shopId, 5);

        Map<String, Object> stats = new HashMap<>();
        stats.put("lowStockProducts", lowStockProducts);
        stats.put("topSellingProducts", topSellingProducts);
        return stats;
    }
}
