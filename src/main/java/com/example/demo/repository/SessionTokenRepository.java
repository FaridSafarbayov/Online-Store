package com.example.demo.repository;

import com.example.demo.model.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
}
