package com.example.demo.controller;


import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Users;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/allUsers")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

//    @PutMapping("/{id}")
////    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
//    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id,
//                                                  @RequestBody @Valid UserDto requestDto) {
//        return new ResponseEntity<>(userService.updateUser(id, requestDto), HttpStatus.OK);
//    }


}
