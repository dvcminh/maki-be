package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.model.Reviews;
import com.miki.animestylebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewsService;

    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviews() {
        return ResponseEntity.ok(reviewsService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable UUID id) {
        return reviewsService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reviews> createReview(@RequestBody Reviews reviews) {
        return ResponseEntity.ok(reviewsService.saveReview(reviews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable UUID id, @RequestBody Reviews reviews) {
        return reviewsService.getReviewById(id)
                .map(existingReview -> {
                    reviews.setId(existingReview.getId());
                    return ResponseEntity.ok(reviewsService.saveReview(reviews));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewsService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}