package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {

    Voucher findByCode(String code);
    Page<Voucher> findByCodeContaining(String name,
                                       Pageable pageable);

    boolean existsByCode(String code);
}

