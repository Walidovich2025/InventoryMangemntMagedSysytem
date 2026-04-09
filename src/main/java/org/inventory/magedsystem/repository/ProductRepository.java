package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDto,Long> {
}
