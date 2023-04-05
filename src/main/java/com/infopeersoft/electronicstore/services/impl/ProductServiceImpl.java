package com.infopeersoft.electronicstore.services.impl;

import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.ProductDto;
import com.infopeersoft.electronicstore.entities.Product;
import com.infopeersoft.electronicstore.exceptions.ResourceNotFoundException;
import com.infopeersoft.electronicstore.helper.Helper;
import com.infopeersoft.electronicstore.repositories.ProductRepository;
import com.infopeersoft.electronicstore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = this.mapper.map(productDto, Product.class);
        Product savedProduct = this.productRepository.save(product);
        return this.mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product updatedProduct = this.productRepository.save(product);
        return this.mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id"));
        this.productRepository.delete(product);
    }

    @Override
    public ProductDto get(Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id"));
        return this.mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public  PageableResponse<ProductDto> getAllLive(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public  PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
