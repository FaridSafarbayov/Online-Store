package com.example.demo.exception;

public class ExistsPhoneNumberException extends RuntimeException{
    public ExistsPhoneNumberException(String s) {
        super(s);
    }
}
