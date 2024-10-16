package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.CreateCategoryRequest;
import com.miki.animestylebackend.dto.request.UpdateCategoryRequest;
import com.miki.animestylebackend.dto.response.CategoryData;
import com.miki.animestylebackend.dto.response.CategoryDto;
import com.miki.animestylebackend.dto.response.GetProductGroupByCategoryData;
import com.miki.animestylebackend.mapper.CategoryMapper;
import com.miki.animestylebackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController extends BaseController{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/all")
    public PageData<CategoryData> getAllCategoriesByNameContaining(@RequestParam(required = false) String name,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        if (name != null) {
            return categoryService.getAllCategoriesByNameContaining(name, page, size);
        }
        return categoryService.getAllCategories(page, size);
    }

    @GetMapping("/product-groupby-category")
    public PageData<GetProductGroupByCategoryData> getProductGroupByCategory(@RequestBody List<UUID> uuids,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        return categoryService.getProductGroupByCategory(uuids, page, size);
    }

    @GetMapping("/get/{id}")
    public CategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    public CategoryDto createCategory(@RequestBody CreateCategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/update/{id}")
    public CategoryDto updateCategory(@PathVariable UUID id, @RequestBody UpdateCategoryRequest category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
