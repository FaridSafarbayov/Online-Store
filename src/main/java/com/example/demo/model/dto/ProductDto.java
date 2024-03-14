package com.example.demo.model.dto;

import com.example.demo.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto{
    private String productName;
    private Long sellerId;
    private String title;
    private Double price;
    private int stockQuantity;
    private String description;
}
