package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import com.miki.animestylebackend.model.DriverReviews;
import com.miki.animestylebackend.model.ShopReviews;

public interface ReviewsMapper {
    ReviewsDto toReviewsDto(ShopReviews ShopReviews, String message);
    ReviewsDto toReviewsDto(DriverReviews ShopReviews, String message);

    ReviewsData toReviewsData(ShopReviews ShopReviews);
    ReviewsData toReviewsData(DriverReviews ShopReviews);
}
