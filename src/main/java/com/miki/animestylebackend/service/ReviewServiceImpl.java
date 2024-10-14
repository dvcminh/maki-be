package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.Reviews;
import com.miki.animestylebackend.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    public Optional<Reviews> getReviewById(UUID id) {
        return reviewsRepository.findById(id);
    }

    public Reviews saveReview(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }

    public void deleteReview(UUID id) {
        reviewsRepository.deleteById(id);
    }
}