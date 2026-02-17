package com.example.payments.Auth.Controller;

import com.example.payments.Auth.DTO.RefreshTokenDTO;
import com.example.payments.Auth.RefreshTokenEnitiy;
import com.example.payments.Auth.Repository.RefreshTokenRepository;
import com.example.payments.Auth.Service.RefreshTokenService;
import com.example.payments.Security.Service.JwtService;
import com.example.payments.Users.DTO.UserLoginResponseDTO;
import com.example.payments.Users.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/refresh-token")
    public UserLoginResponseDTO renewAccessToken(
            @RequestBody RefreshTokenDTO refreshTokenDTO
    ){
        RefreshTokenEnitiy refreshToken = refreshTokenRepository.findByToken(refreshTokenDTO.token())
                .orElseThrow(
                        () -> new RuntimeException("invalid token")
                );
        refreshTokenService.verifyRefreshTokenExpiry(refreshToken);
        String newAccessToken = jwtService.returnGeneratedToken(refreshToken.getUser());
        return new UserLoginResponseDTO(
                newAccessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }
}
