package com.campus.food.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.campus.food.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);
}