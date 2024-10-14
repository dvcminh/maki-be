package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.Reviews;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {
    List<Reviews> getAllReviews();
    Optional<Reviews> getReviewById(UUID id);
    Reviews saveReview(Reviews reviews);
    void deleteReview(UUID id);
}
