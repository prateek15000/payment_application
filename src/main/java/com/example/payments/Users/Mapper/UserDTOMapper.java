package com.example.payments.Users.Mapper;

import com.example.payments.Users.DTO.UserCreationDTO;
import com.example.payments.Users.DTO.UserResponseDTO;
import com.example.payments.Users.Entity.UserEnitiy;
import com.example.payments.Users.Roles.Roles;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserEnitiy convertToUser(
            UserCreationDTO userCreationDTO
    ){
        UserEnitiy user = new UserEnitiy();
        user.setUserFirstName(userCreationDTO.userFirstName());
        user.setUserLastName(userCreationDTO.userLastName());
        user.setUserEmail(userCreationDTO.userFirstName());
        user.setUserPassword(userCreationDTO.userPassword());
        user.setUserPhoneNumber(userCreationDTO.userPhoneNumber());
        user.setUserRoles(Roles.USER);
        return user;
    }

    public UserResponseDTO userResponseDTO(
            UserEnitiy user
    ){
        return new UserResponseDTO(
                user.getUserId(),
                user.getUserFirstName(),
                user.getUserLastName(),
                user.getUserEmail()
        );
    }

}
