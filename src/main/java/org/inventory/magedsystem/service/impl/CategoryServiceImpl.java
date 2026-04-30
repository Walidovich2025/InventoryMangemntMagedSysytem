package org.inventory.magedsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.UserDto;
import org.inventory.magedsystem.entity.Category;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.CategoryRepository;
import org.inventory.magedsystem.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response createCategory(CategoryDto categoryDto) {
        Category categoryToSave=modelMapper.map(categoryDto,Category.class);
        categoryRepository.save(categoryToSave);
        return Response.builder()
                .status(200)
                .message("category created successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories=categoryRepository.findAll(Sort.by(Sort.Direction.DESC,"Id"));
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();


        return Response.builder()
                .status(200)
                .message("success")
                .categoriesDTO(categoryDtos)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category not found"));
        CategoryDto categoryDto=modelMapper.map(category,CategoryDto.class);
        return Response.builder()
                .status(200)
                .message("success")
                .categoryDto(categoryDto)
                .build();
    }

    @Override
    public Response updateCategory(Long id,CategoryDto categoryDto) {
        Category exitingcCtegory=categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("category not found"));
        exitingcCtegory.setName(categoryDto.getName());
        categoryRepository.save(exitingcCtegory);
        return Response.builder()
                .status(200)
                .message("category updated successfully")
                .build();

    }

    @Override
    public Response deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("category not found"));
        categoryRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("category deleted successfully")
                .build();
    }
}
