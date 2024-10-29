package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, UUID> {
}