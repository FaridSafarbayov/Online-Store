package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String reviewText;
    private int rating;
//    private LocalDateTime reviewDate = LocalDateTime.now();


}
