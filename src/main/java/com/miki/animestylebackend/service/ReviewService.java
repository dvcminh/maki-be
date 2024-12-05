package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.SubmitReview;
import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.UUID;

public interface ReviewService {
    PageData<ReviewsData> getShopReviews(int page, int size, UUID shopId, UUID driverId, BigDecimal startRating, BigDecimal endRating, Sort.Direction direction, String sortBy);
    ReviewsDto getReviewById(UUID id);
    ReviewsDto saveReview(SubmitReview reviews);
    void deleteReview(UUID id);
}
