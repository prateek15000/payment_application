package com.example.payments.Auth.Repository;

import com.example.payments.Auth.RefreshTokenEnitiy;
import com.example.payments.Users.Entity.UserEnitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEnitiy , Long> {
    Optional<RefreshTokenEnitiy> findByToken(String token);
    Optional<RefreshTokenEnitiy> findByUser(UserEnitiy user);
}
