package com.example.payments.Users.DTO;

public record UserLoginRequestDTO(
        String userEmail,
        String userPassword
) {
}
