package com.example.demo.service;

import com.example.demo.exception.AddressNotfoundException;
import com.example.demo.exception.ExistsEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.Users;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    public AddressDto createAddress(Long userId, AddressDto addressDto) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        if (addressRepository.existsByUser_Id(userId)) {
            throw new ExistsEmailException("this userId already have a address ");
        }
        Address address = modelMapper.map(addressDto,Address.class);
        users.setAddress(address);
        address.setUser(users);
        addressRepository.save(address);
        return addressDto;

    }
    public AddressDto updateAddress(Long userId, AddressDto addressDto) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        if (users != null) {
            Address updateAddress = modelMapper.map(addressDto,Address.class);
            updateAddress.setId(users.getAddress().getId());
            updateAddress.setUser(users);
            users.setAddress(updateAddress);
            addressRepository.save(updateAddress);
        }
        return addressDto;
    }
    public void deleteAddress(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        Address address = users.getAddress();
        if (address == null) {
            throw new AddressNotfoundException("address not found");
        }
        users.setAddress(null);

        addressRepository.deleteById(address.getId());
    }
    public AddressDto getAddressById(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return modelMapper.map(users.getAddress(),AddressDto.class);
    }
    public List<AddressDto> getAllAddress() {
        List<Address> addressList = addressRepository.findAll();
        return addressList.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
    }
}
