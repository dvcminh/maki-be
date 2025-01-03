package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.SubmitReview;
import com.miki.animestylebackend.dto.response.DriverDto;
import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.mapper.ReviewsMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.DriverRepository;
import com.miki.animestylebackend.repository.jpa.DriverReviewsRepository;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
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
    private final DriverRepository driverRepository;
    private final ShopRepository shopRepository;

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
        if (submitReview.getId() != null) {
            ShopReviews shopReviews = shopReviewsRepository.findById(submitReview.getId())
                    .orElseThrow(() -> new RuntimeException("Review not found"));
            shopReviews.setRating(submitReview.getRating());
            shopReviews.setComment(submitReview.getComment());
            return reviewsMapper.toReviewsDto(shopReviewsRepository.save(shopReviews), "Update review successfully");
        }
        User user = userService.getUserById(submitReview.getUserId());
        Order order = orderService.getOrderById(submitReview.getOrderId());
        if (submitReview.getIsShop()) {
            Shop shop = shopRepository.findById(submitReview.getShopId())
                    .orElseThrow(() -> new NotFoundException("Shop not found"));
            ShopReviews shopReviews = ShopReviews.builder()
                    .rating(submitReview.getRating())
                    .comment(submitReview.getComment())
                    .shop(shop)
                    .order(order)
                    .user(user)
                    .build();
            return reviewsMapper.toReviewsDto(shopReviewsRepository.save(shopReviews), "Save review successfully");
        }
        Driver driver = driverRepository.findById(submitReview.getDriverId())
                .orElseThrow(() -> new NotFoundException("Driver not found"));
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