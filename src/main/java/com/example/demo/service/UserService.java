package com.example.demo.service;

import com.example.demo.enums.Role;
import com.example.demo.exception.*;
import com.example.demo.model.dto.AuthenticationResponseDto;
import com.example.demo.model.dto.LoginDto;
import com.example.demo.model.dto.RegistrationDto;
import com.example.demo.model.dto.UserDto;

import com.example.demo.model.entity.SessionToken;
import com.example.demo.model.entity.Users;
import com.example.demo.repository.SessionTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.JwtAuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionTokenRepository sessionTokenRepository;
    public UserDto addUser(UserDto userDto) {
        System.out.println(userDto);
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ExistsEmailException("this email already used " + userDto.getEmail());
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new ExistsPhoneNumberException("this phone number already used");
        }
      //  User user1=modelMapper.map(userDto,User.class);
//        Address address=modelMapper.map(userDto.getAddress(),Address.class);
//        Address address=user.getAddress();
//        user.setAddress(address);
//        address.setUser(user);
       Users user= modelMapper.map(userDto, Users.class);
        userRepository.save(user);


       return userDto;
    }

    public UserDto getById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
       return modelMapper.map(user,UserDto.class);

    }

    public List<UserDto> getAllUser() {
        List<Users> userListEntity = userRepository.findAll();


        List<UserDto> userDtoList = userListEntity.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtoList;
    }

    public String deleteById(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
        try {
            userRepository.delete(users);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "Success";
    }
//    public UserDto updateUser(Long id, UserDto userDto) {
//        User existUser = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
//        if (existUser != null) {
//            User updateUsers= modelMapper.map(userDto,User.class);
//            Address updateAddress=modelMapper.map(userDto.getAddress(),Address.class);
//            updateAddress.setId(existUser.getAddress().getId());
//            updateUsers.setId(existUser.getId());
//            updateUsers.setAddress(updateAddress);
//            updateAddress.setUser(updateUsers);
//            userRepository.save(updateUsers);
//            return userDto;
//        }
//        return null;
//    }

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


    public void signUpUser(RegistrationDto registrationDto) throws MessagingException {
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
                .role(Role.USER)
                .password(passwordEncoder.encode(registrationDto.getPassword())).build();
        userRepository.save(user);
    }

    public AuthenticationResponseDto login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            throw new UserNotFoundException("User not found");
        }
        String password = loginDto.getPassword();
        Users user = userRepository.findByEmail(email).get();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Successful retrieval of the email!");
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("The entered password is correct. Transaction successful!! ");

            String token = jwtService.generateToken(user.getEmail());
            SessionToken sessionToken = new SessionToken();

            sessionToken.setUserId(user.getId());
            sessionToken.setAccessToken(token);
            sessionToken.setCreateDate(LocalDateTime.now());
            sessionTokenRepository.save(sessionToken);
            AuthenticationResponseDto authenticationResponseDto=new AuthenticationResponseDto();

           authenticationResponseDto.setToken(token);
            return authenticationResponseDto;
        } else {
            throw new RuntimeException("jhvbewc");
        }
    }


}
