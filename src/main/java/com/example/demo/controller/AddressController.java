package com.example.demo.controller;

import com.example.demo.model.dto.AddressDto;

import com.example.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<AddressDto> createAddress(@RequestParam Long userId,
                                                    @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.createAddress(userId, addressDto), HttpStatus.CREATED);

    }

    @PutMapping("/{userId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<AddressDto> updateAddress(@RequestParam Long userId,
                                                    @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.updateAddress(userId, addressDto), HttpStatus.OK);

    }
    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> deleteAddress(@RequestParam Long userId) {
        addressService.deleteAddress(userId);
        return ResponseEntity.ok("Success");

    }
    @GetMapping("/{userId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<AddressDto> getAddressById(@RequestParam Long userId) {
        return new ResponseEntity<>(addressService.getAddressById(userId), HttpStatus.OK);

    }
    @GetMapping()
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        return new ResponseEntity<>(addressService.getAllAddress(), HttpStatus.OK);

    }
}
