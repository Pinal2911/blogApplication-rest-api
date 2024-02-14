package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDto;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","id","categoryId"));

        return modelMapper.map(category,CategoryDto.class);


    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map((category)->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
        Category category=categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("category","id","id"));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());


        Category updatedCategory=categoryRepository.save(category);
        return modelMapper.map(updatedCategory,CategoryDto.class);

    }

    @Override
    public void deleteCategory(long id) {
        Category category=categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category","id","category"));
        categoryRepository.delete(category);

    }
}
