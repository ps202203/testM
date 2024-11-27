package com.nexon.maplestory.repository;

import com.nexon.maplestory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByApiKey(String apiKey);
}