package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.ProductDto;
import org.inventory.magedsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
