package org.example.sigaut_backend.repository;

import org.example.sigaut_backend.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}