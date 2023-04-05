package com.infopeersoft.electronicstore.services;

import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);
    //update
    ProductDto update(ProductDto productDto,Long productId);
    //delete
    void delete(Long productId);
    //getSingle
    ProductDto get(Long productId);
    //get all
    PageableResponse<ProductDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get all :live
   PageableResponse<ProductDto> getAllLive(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);


}
