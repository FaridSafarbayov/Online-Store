package com.example.demo.controller;


import com.example.demo.model.dto.ReviewDto;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("addReview")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto,
                                                  @RequestParam Long userId,
                                                  @RequestParam Long productId) {
        return new ResponseEntity<>(reviewService.cretaReview(reviewDto, userId, productId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> deleteRatingById(@PathVariable Long id) {
        reviewService.deleteRatingById(id);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }


    @GetMapping()
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<ReviewDto>> getAllReview() {
        return ResponseEntity.ok(reviewService.getAllReview());
    }
}
