package com.example.demo.repository;

import com.example.demo.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
//    Optional<User> findByUserId(Long userId);
    boolean existsByUser_Id(Long userId);
}
