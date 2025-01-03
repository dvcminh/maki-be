package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Page<Driver> findAll(Pageable pageable);

    Page<Driver> findByUser_EmailContains(String email, Pageable pageable);
}