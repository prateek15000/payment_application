package com.example.payments.Users.Repository;

import com.example.payments.Users.Entity.UserEnitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEnitiy , Long> {
    Optional<UserEnitiy> findByEmail(String userEmail);
}
