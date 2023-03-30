package com.infopeersoft.electronicstore.services;

import com.infopeersoft.electronicstore.dtos.CategoryDto;
import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto update(CategoryDto categoryDto,Long categoryId);
    //delete
    void delete(Long categoryId);
    //getall
    PageableResponse<CategoryDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
    //get Single category
    CategoryDto get(Long categoryId);
    //search
    List<CategoryDto> searchCategory(String keyword);

}
