package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.SubmitReview;
import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import com.miki.animestylebackend.mapper.ReviewsMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.DriverReviewsRepository;
import com.miki.animestylebackend.repository.jpa.ShopReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewsMapper reviewsMapper;
    private final ShopReviewsRepository shopReviewsRepository;
    private final DriverReviewsRepository driverReviewsRepository;
    private final ShopService shopService;
    private final UserService userService;
    private final OrderService orderService;
    private final DriverService driverService;

    public PageData<ReviewsData> getShopReviews(int page, int size, UUID shopId, UUID driverId, BigDecimal startRating, BigDecimal endRating, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));

        if (shopId != null) {
            Page<ReviewsData> reviewsData = shopReviewsRepository
                    .findByShop_IdAndRatingBetweenOrderByCreatedAtAsc(shopId, startRating, endRating, pageable)
                    .map(reviewsMapper::toReviewsData);
            return new PageData<>(reviewsData, "Get products by list id successfully");
        }
        Page<ReviewsData> reviewsData = driverReviewsRepository
                .findByDriver_IdAndRatingBetweenOrderByCreatedAtAsc(driverId, startRating, endRating, pageable)
                .map(reviewsMapper::toReviewsData);
        return new PageData<>(reviewsData, "Get products by list id successfully");

    }

    public ReviewsDto getReviewById(UUID id) {
        return reviewsMapper.toReviewsDto(shopReviewsRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Review not found")), "Get review by id successfully");
    }

    public ReviewsDto saveReview(SubmitReview submitReview) {
        User user = userService.getUserById(submitReview.getUserId());
        Order order = orderService.getOrderById(submitReview.getOrderId());
        if (submitReview.getIsShop()) {
            Shop shop = shopService.getShopById(submitReview.getShopId());
            ShopReviews shopReviews = ShopReviews.builder()
                    .rating(submitReview.getRating())
                    .comment(submitReview.getComment())
                    .shop(shop)
                    .order(order)
                    .user(user)
                    .build();
            return reviewsMapper.toReviewsDto(shopReviewsRepository.save(shopReviews), "Save review successfully");
        }
        Driver driver = driverService.getDriverById(submitReview.getDriverId());
        DriverReviews driverReviews = DriverReviews.builder()
                .rating(submitReview.getRating())
                .comment(submitReview.getComment())
                .user(user)
                .order(order)
                .driver(driver)
                .build();
        return reviewsMapper.toReviewsDto(driverReviewsRepository.save(driverReviews), "Save review successfully");
    }

    public void deleteReview(UUID id) {
        shopReviewsRepository.deleteById(id);
    }
}