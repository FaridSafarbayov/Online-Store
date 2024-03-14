package com.example.demo.model.dto;


import com.example.demo.model.entity.Address;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
//    private boolean enabled;
   private AddressDto address;


}
