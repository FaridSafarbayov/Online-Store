package com.example.demo.service;

import com.example.demo.enums.Role;
import com.example.demo.exception.ExistsEmailException;
import com.example.demo.exception.ExistsPhoneNumberException;
import com.example.demo.exception.MessagingException;
import com.example.demo.model.dto.RegistrationDto;
import com.example.demo.model.entity.Users;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signUpAdmin(RegistrationDto registrationDto) throws MessagingException {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new ExistsEmailException("this email already exist");
        }
        if (userRepository.existsByPhoneNumber(registrationDto.getPhoneNumber())) {
            throw new ExistsPhoneNumberException("this phone number already used");
        }

        var user = Users.builder().firstName(registrationDto.getFirstName()).lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .phoneNumber(registrationDto.getPhoneNumber())
                .createAt(LocalDateTime.now())
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(registrationDto.getPassword())).build();


        userRepository.save(user);
    }
}
