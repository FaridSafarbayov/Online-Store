package com.example.demo.exception;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ExistsEmailException extends RuntimeException{
    public ExistsEmailException(@NotBlank(message = "email can not be null") @Pattern(regexp = "[\\w.-]+@[\\w.-]+.\\w+$") String s) {
        super(s);
    }
}
