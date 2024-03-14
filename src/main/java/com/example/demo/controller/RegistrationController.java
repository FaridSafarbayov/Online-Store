package com.example.demo.controller;

import com.example.demo.exception.MessagingException;
import com.example.demo.exception.PasswordException;
import com.example.demo.model.dto.AuthenticationResponseDto;
import com.example.demo.model.dto.LoginDto;
import com.example.demo.model.dto.RegistrationDto;
import com.example.demo.model.entity.Users;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

@PostMapping("/registerAsAdmin")
public ResponseEntity<String> registerAsCustomer(@RequestBody RegistrationDto registrationDto) throws MessagingException {
    userService.signUpAdmin(registrationDto);
    return ResponseEntity.ok("Registration successful." +
            "Please verify your account for to do activate during 2 minutes.Check out your gmail.");
}

    @PostMapping("/userSignUp")
    public ResponseEntity<String> signUpUser(@RequestBody @Valid RegistrationDto registrationDto) throws MessagingException {
        userService.signUpUser(registrationDto);
        return ResponseEntity.ok("Registration successful." +
                "Please verify your account for to do activate during 2 minutes.Check out your gmail.");
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

}
