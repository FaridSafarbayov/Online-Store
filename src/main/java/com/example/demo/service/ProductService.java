package com.example.demo.service;

import com.example.demo.exception.OrdersNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {
    private ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
//        for (Review reviewDto : productDto.getReview()) {
//            Review review = modelMapper.map(reviewDto, Review.class);
//            review.setProduct(product);
//            product.addReview(review);
//        }
//        Product product1=new Product();
//        product1.setProductName(product.getProductName());
//        product1.setId(product1.getId());
//        product1.setOrder(product1.getOrder());
//        product1.setPrice(product.getPrice());
//        product1.setDescription(product.getDescription());
//        product1.setStockQuantity(product.getStockQuantity());
//        product1.setSellerId(product.getId());


        return modelMapper.map(product, ProductDto.class);
    }

    public List<ProductDto> getAllProduct() {
        List<Product> productListEntity = productRepository.findAll();


        List<ProductDto> productDtoList = productListEntity.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        return productDtoList;
    }

    public String deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(product -> {
                    productRepository.deleteById(product.getId());
                }, () -> {
                    throw new ProductNotFoundException("This id not found :" + id);
                }
        );
        return "Success";
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return modelMapper.map(productOptional.get(), ProductDto.class);
        } else {
            throw new OrdersNotFoundException("Product not found with id: " + id);
        }
    }


    public ProductDto updateProductById(Long id,ProductDto productDto) {
        Product exist = productRepository.findById(id).get();
        Product product= modelMapper.map(productDto,Product.class);
        exist.setId(product.getId());
        exist.setProductName(product.getProductName());
        exist.setSellerId(product.getSellerId());
        exist.setPrice(product.getPrice());
        exist.setStockQuantity(product.getStockQuantity());
        exist.setDescription(product.getDescription());
        exist.setOrder(product.getOrder());
        exist.setReviews(product.getReviews());
//        exist.setReview(product.getReview());
        Product newProduct = productRepository.save(exist);
        return modelMapper.map(newProduct,ProductDto.class);
    }
}

