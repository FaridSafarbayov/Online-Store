package com.example.demo.controller;
import com.example.demo.model.dto.ProductDto;

import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        System.out.println("addProduct method called");
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/allProducts")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));

    }
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDtoList = productService.getProductById(id);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }
    @PutMapping("update/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.updateProductById(id,productDto),HttpStatus.OK);
    }
}

