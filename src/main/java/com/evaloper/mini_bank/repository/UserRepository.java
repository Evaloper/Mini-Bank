package com.evaloper.mini_bank.repository;


import com.evaloper.mini_bank.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByPhoneNumber(String phoneNumber);
    boolean existsByAccountNumber(String accountNumber);

    UserEntity findByAccountNumber(String accountNumber);

    Optional<UserEntity> findByEmail(String email);


}

