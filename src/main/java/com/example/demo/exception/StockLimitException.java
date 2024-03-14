package com.example.demo.exception;

public class StockLimitException extends Throwable{
    public StockLimitException(String s) {
        super(s);
    }
}
