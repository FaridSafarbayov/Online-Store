package com.example.demo.exception;

public class AddressNotfoundException extends RuntimeException{
    public AddressNotfoundException(String s) {
        super(s);
    }
}
