package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.CategoryDto;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    public CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory=categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable ("id") Long categoryId){
        CategoryDto categoryDto=categoryService.getCategory((categoryId));
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
      List<CategoryDto> categories=categoryService.getAllCategories();
      return ResponseEntity.ok(categories);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody  CategoryDto categoryDto,
                                                      @PathVariable("id") Long id) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public String deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return "category deleted successfully";
    }
}
