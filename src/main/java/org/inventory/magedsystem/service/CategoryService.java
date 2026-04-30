package org.inventory.magedsystem.service;

import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryDto);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id, CategoryDto categoryDto);
    Response deleteCategory(Long id);

}
