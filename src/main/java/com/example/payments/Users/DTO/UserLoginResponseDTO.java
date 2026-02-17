package com.example.payments.Users.DTO;

public record UserLoginResponseDTO(
    String accessToken,
    String refreshToken,
    String tokenType
) {
}
