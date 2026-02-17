package com.example.payments.Users.DTO;

public record UserResponseDTO(
        Long userId,
        String userFirstName,
        String userLastName,
        String userEmail
) {
}
