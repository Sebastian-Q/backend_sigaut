package org.example.sigaut_backend.repository;

import org.example.sigaut_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByBarCode(String barCode);
}
