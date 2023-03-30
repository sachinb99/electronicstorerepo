package com.infopeersoft.electronicstore.services.impl;

import com.infopeersoft.electronicstore.dtos.CategoryDto;
import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;
import com.infopeersoft.electronicstore.entities.Category;
import com.infopeersoft.electronicstore.entities.User;
import com.infopeersoft.electronicstore.exceptions.ResourceNotFoundException;
import com.infopeersoft.electronicstore.helper.Helper;
import com.infopeersoft.electronicstore.repositories.CategoryRepository;
import com.infopeersoft.electronicstore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("In CategoryServiceImpl class  searchCategory method start");
        Category category = this.mapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepository.save(category);
        log.info("In CategoryServiceImpl class  searchCategory method ended");
        return this.mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        log.info("In CategoryServiceImpl class  update method start with categoryId"+categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = this.categoryRepository.save(category);
        log.info("In CategoryServiceImpl class  update method ended");
        return this.mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(Long categoryId) {
        log.info("In CategoryServiceImpl class  delete method start with categoryId"+categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        this.categoryRepository.delete(category);
        log.info("In CategoryServiceImpl class  delete method ended");
    }

    @Override
    public PageableResponse<CategoryDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        log.info("In CategoryServiceImpl class  getAll method started");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("In CategoryServiceImpl class  getAll method ended");
        return pageableResponse;
    }

    @Override
    public CategoryDto get(Long categoryId) {
        log.info("In CategoryServiceImpl class  get method started with categoryId"+categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        log.info("In CategoryServiceImpl class  get method ended");
        return this.mapper.map(category,CategoryDto.class);
    }
    public List<CategoryDto> searchCategory(String keyword) {
        log.info("In CategoryServiceImpl class  searchCategory method started");
        List<Category> categories = this.categoryRepository.findBytitleContaining(keyword);
        List<CategoryDto> dtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        log.info("In CategoryServiceImpl class  searchCategory method ended");
        return dtoList;
    }
}
