package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findByNameContaining(String name,
                                        Pageable pageable);

    void deleteById(UUID categoryId);


    Optional<Category> findByNameIgnoreCase(String name);

    boolean existsByName(String name);



//    Page<GetProductGroupByCategoryData> getProductGroupByCategory(List<UUID> uuids, Pageable pageable);
}
