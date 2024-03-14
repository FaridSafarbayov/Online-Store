package com.example.demo.model.mapper;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Users;


public interface UserMapper {
    Users userDtoToEntity(UserDto userDto);
    UserDto entityToUserDto(Users user);
}
