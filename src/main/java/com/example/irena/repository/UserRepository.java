package com.example.irena.repository;

import com.example.irena.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findByCode(String code) ;
}
