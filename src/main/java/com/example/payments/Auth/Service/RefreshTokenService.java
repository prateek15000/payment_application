package com.example.payments.Auth.Service;

import com.example.payments.Auth.RefreshTokenEnitiy;
import com.example.payments.Auth.Repository.RefreshTokenRepository;
import com.example.payments.Users.Entity.UserEnitiy;
import com.example.payments.Users.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenEnitiy createRefreshToken(UserEnitiy user){
        RefreshTokenEnitiy refreshToken =refreshTokenRepository.findByUser(user)
                .orElseGet(
                        () -> {
                            RefreshTokenEnitiy token = new RefreshTokenEnitiy();
                            token.setUser(user);
                            return token;
                        }
                );
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiration(
                Instant.now().plus(7, ChronoUnit.DAYS)
        );
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenEnitiy verifyRefreshTokenExpiry(RefreshTokenEnitiy refreshToken){
        if(refreshToken.getExpiration().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("token expired");
        }
        return refreshToken;
    }

}
