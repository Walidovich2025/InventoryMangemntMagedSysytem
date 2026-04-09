package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDto,Long> {
}
