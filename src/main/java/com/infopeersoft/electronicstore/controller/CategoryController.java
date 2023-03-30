package com.infopeersoft.electronicstore.controller;

import com.infopeersoft.electronicstore.config.AppConstants;
import com.infopeersoft.electronicstore.dtos.ApiResponse;
import com.infopeersoft.electronicstore.dtos.CategoryDto;
import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;
import com.infopeersoft.electronicstore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("In CategoryController class  createCategory method start");
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        log.info("In CategoryController class  createCategory method ended");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        log.info("In CategoryController class  updateCategory method start with categoryId"+categoryId);
        CategoryDto updatedCategory = this.categoryService.update(categoryDto, categoryId);
        log.info("In CategoryController class  updateCategory method ended");
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        log.info("In CategoryController class  deleteCategory method started with categoryId"+categoryId);
        this.categoryService.delete(categoryId);
        ApiResponse response = ApiResponse.builder().message(AppConstants.DELETE).status(HttpStatus.OK).success(true).build();
        log.info("In CategoryController class  deleteCategory method ended");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    //getall
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT1_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        log.info("In CategoryController class  getAll method started");
        PageableResponse<CategoryDto> pageableResponse = this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("In CategoryController class  getAll method ended");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //getSingle
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable Long categoryId){
        log.info("In CategoryController class  getSingle method started with categoryId"+categoryId);
        CategoryDto categoryDto = this.categoryService.get(categoryId);
        log.info("In CategoryController class  getSingle method ended");
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keywords){
        log.info("In CategoryController class  searchCategory method started");
        List<CategoryDto> categoryDtos = this.categoryService.searchCategory(keywords);
        log.info("In CategoryController class  searchCategory method ended");
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

}
