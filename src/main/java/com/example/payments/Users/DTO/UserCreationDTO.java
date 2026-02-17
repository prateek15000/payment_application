package com.example.payments.Users.DTO;

public record UserCreationDTO(
        String userFirstName,
        String userLastName,
        String userEmail,
        String userPhoneNumber,
        String userPassword
) {
}
