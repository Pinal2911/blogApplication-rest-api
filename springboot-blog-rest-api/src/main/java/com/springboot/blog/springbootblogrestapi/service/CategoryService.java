package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDto;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto,long id);

    public void deleteCategory(long id);


}
