package com.example.demo.exception;

public class ReviewNotFound extends RuntimeException{
    public ReviewNotFound(String s) {
        super(s);
    }
}
