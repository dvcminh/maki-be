package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.SubmitReview;
import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import com.miki.animestylebackend.model.ShopReviews;
import com.miki.animestylebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewsService;

    @GetMapping
    public ResponseEntity<PageData<ReviewsData>> getShopReviews(@RequestParam(name = "shopId", required = false) UUID shopId,
                                                                @RequestParam(name = "driverId", required = false) UUID driverId,
                                                                @RequestParam(defaultValue = "4.0") BigDecimal startRating,
                                                                @RequestParam(defaultValue = "5.0") BigDecimal endRating,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                                @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy) {
        return ResponseEntity.ok(reviewsService.getShopReviews(page, size, shopId, driverId, startRating, endRating, sort, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewsDto> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewsService.getReviewById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewsDto> createReview(@RequestBody SubmitReview reviews) {
        return ResponseEntity.ok(reviewsService.saveReview(reviews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewsDto> updateReview(@PathVariable UUID id, @RequestBody ShopReviews shopReviews) {
        return ResponseEntity.ok(reviewsService.getReviewById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewsService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}