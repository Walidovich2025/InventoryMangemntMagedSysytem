package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
