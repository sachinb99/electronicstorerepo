package com.infopeersoft.electronicstore.controller;

import com.infopeersoft.electronicstore.config.AppConstants;
import com.infopeersoft.electronicstore.dtos.ApiResponse;
import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.ProductDto;
import com.infopeersoft.electronicstore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("In ProductController class  createProduct method start");
        ProductDto createProduct = this.productService.create(productDto);
        log.info("In ProductController class  createProduct method ended");
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,@RequestBody ProductDto productDto){
        log.info("In ProductController class  updateProduct method started with id :{}",productId);
        ProductDto updatedProduct = this.productService.update(productDto, productId);
        log.info("In ProductController class  updateProduct method ended");
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long productId){
        log.info("In ProductController class  delete method start with id :{}",productId);
        this.productService.delete(productId);
        ApiResponse response = ApiResponse.builder().message(AppConstants.DELETE).status(HttpStatus.OK).success(true).build();
        log.info("In ProductController class  delete method ended");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        log.info("In ProductController class  getProduct method start with id {}:",productId);
        ProductDto productDto = this.productService.get(productId);
        log.info("In ProductController class  getProduct method ended");
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(   @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                  @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                  @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT1_BY,required = false)String sortBy,
                                                                  @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        log.info("In ProductController class  getAll method start");
        PageableResponse<ProductDto> pageableResponse = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class  getAll method ended");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

    //get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(   @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                  @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                  @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT1_BY,required = false)String sortBy,
                                                                  @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        log.info("In ProductController class  getAllLive method start");
        PageableResponse<ProductDto> pageableResponse = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class  getAllLive method ended");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

    //search all
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT1_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        log.info("In ProductController class  searchProduct method start");
        PageableResponse<ProductDto> pageableResponse = this.productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class  searchProduct method ended");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }
}
