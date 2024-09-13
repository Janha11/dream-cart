package com.synergy.dreamShop.controller;

import com.synergy.dreamShop.exceptions.AlreadyExistsException;
import com.synergy.dreamShop.exceptions.ResourceNotFoundException;
import com.synergy.dreamShop.model.Category;
import com.synergy.dreamShop.response.ApiResponse;
import com.synergy.dreamShop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @Autowired
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllCategories(){
        try{
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error :",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestParam Category name){
        try {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success", theCategory));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long id){
        try {
            Category thecategory=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found!",thecategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping(" /category/{name}/category")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String name){
        try {
            Category thecategory=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found!",thecategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping ("/category/{id}/delete")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryId(id);
            return ResponseEntity.ok(new ApiResponse("Deleted!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("category/{id}/update")
    public ResponseEntity<ApiResponse>updateCategory(@RequestParam Category category,@PathVariable Long id){
        try {
            Category updatecategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Update Success",updatecategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }



}
