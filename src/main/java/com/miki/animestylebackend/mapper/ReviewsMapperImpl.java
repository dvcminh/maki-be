package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.ReviewsData;
import com.miki.animestylebackend.dto.response.ReviewsDto;
import com.miki.animestylebackend.model.DriverReviews;
import com.miki.animestylebackend.model.ShopReviews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewsMapperImpl implements ReviewsMapper {
    private final ShopMapper shopMapper;
    private final DriverMapper driverMapper;
    private final OrderMapper orderMapper;
    @Override
    public ReviewsDto toReviewsDto(ShopReviews ShopReviews, String message) {
        if (ShopReviews == null) {
            return null;
        }

        ReviewsData ReviewsData = toReviewsData(ShopReviews);

        ReviewsDto ReviewsDto = new ReviewsDto();
        ReviewsDto.setSuccess(true);
        ReviewsDto.setStatus(200);
        ReviewsDto.setMessage(message);
        ReviewsDto.setData(ReviewsData);
        ReviewsDto.setTimestamp(LocalDateTime.now());

        return ReviewsDto;
    }

    @Override
    public ReviewsDto toReviewsDto(DriverReviews driverReviews, String message) {
        if (driverReviews == null) {
            return null;
        }

        ReviewsData ReviewsData = toReviewsData(driverReviews);

        ReviewsDto ReviewsDto = new ReviewsDto();
        ReviewsDto.setSuccess(true);
        ReviewsDto.setStatus(200);
        ReviewsDto.setMessage(message);
        ReviewsDto.setData(ReviewsData);
        ReviewsDto.setTimestamp(LocalDateTime.now());

        return ReviewsDto;
    }

    @Override
    public ReviewsData toReviewsData(ShopReviews ShopReviews) {
        if (ShopReviews == null) {
            return null;
        }

        ReviewsData ReviewsData = new ReviewsData();
        ReviewsData.setUserId(ShopReviews.getId());
        ReviewsData.setShopId(shopMapper.toShopData(ShopReviews.getShop()));
        ReviewsData.setOrderId(orderMapper.toOrderData(ShopReviews.getOrder()));
        ReviewsData.setRating(ShopReviews.getRating());
        ReviewsData.setComment(ShopReviews.getComment());

        return ReviewsData;
    }

    @Override
    public ReviewsData toReviewsData(DriverReviews ShopReviews) {
        if (ShopReviews == null) {
            return null;
        }

        ReviewsData ReviewsData = new ReviewsData();
        ReviewsData.setUserId(ShopReviews.getId());
        ReviewsData.setDriverId(driverMapper.toDriverData(ShopReviews.getDriver()));
        ReviewsData.setOrderId(orderMapper.toOrderData(ShopReviews.getOrder()));
        ReviewsData.setRating(ShopReviews.getRating());
        ReviewsData.setComment(ShopReviews.getComment());

        return ReviewsData;
    }
}