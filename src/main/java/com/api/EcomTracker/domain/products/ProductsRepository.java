package com.api.EcomTracker.domain.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> findAllByActiveTrue (Pageable pagination);
}
