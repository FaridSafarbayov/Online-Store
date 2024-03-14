package com.example.demo.service;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.ReviewNotFound;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.ReviewDto;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Review;
import com.example.demo.model.entity.Users;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    public ReviewDto cretaReview(ReviewDto reviewDto,Long userId,Long productId) {
        Review review=modelMapper.map(reviewDto,Review.class);
        Users user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not fount" + userId));
        Product product=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not found"+productId));
        review.setUser(user);
        review.setProduct(product);
//        review.setReviewText(reviewDto.getReviewText());
//        review.setRating(reviewDto.getRating());
        reviewRepository.save(review);

        return  reviewDto;
    }

    public void deleteRatingById(Long id) {
        Review reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound("this id not found :" + id));
        reviewRepository.delete(reviewEntity);
    }

    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound("this id not found :" + id));

        return modelMapper.map(review,ReviewDto.class);

    }
    public List<ReviewDto> getAllReview() {
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewDto> reviewDtoList = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .toList();
        return reviewDtoList;
    }
}
