package com.example.demo.exception;

public class OrdersNotFoundException extends RuntimeException{
    public OrdersNotFoundException(String s) {
        super(s);
    }
}
