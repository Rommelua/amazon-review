package com.amazon.review.repository;

import com.amazon.review.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByProductId(String productId);
}
