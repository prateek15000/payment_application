package com.example.payments.Users.Service;

import com.example.payments.Auth.RefreshTokenEnitiy;
import com.example.payments.Auth.Service.RefreshTokenService;
import com.example.payments.Security.Service.JwtService;
import com.example.payments.Users.DTO.UserCreationDTO;
import com.example.payments.Users.DTO.UserLoginRequestDTO;
import com.example.payments.Users.DTO.UserLoginResponseDTO;
import com.example.payments.Users.DTO.UserResponseDTO;
import com.example.payments.Users.Entity.UserEnitiy;
import com.example.payments.Users.Mapper.UserDTOMapper;
import com.example.payments.Users.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    public UserResponseDTO newUser(
            UserCreationDTO userCreationDTO
    ){
        UserEnitiy user = userMapper.convertToUser(userCreationDTO);
        user.setUserPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepository.save(user);
        return userMapper.userResponseDTO(savedUser);

    }

    public UserLoginResponseDTO loginUser(UserLoginRequestDTO loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.userEmail(),
                        loginRequest.userPassword()
                )
        );

        UserEnitiy user = userRepository.findByUserEmail(loginRequest.userEmail())
                .orElseThrow(
                        () -> new RuntimeException("user email")
                );
        String accessToken = jwtService.returnGeneratedToken(user);
        RefreshTokenEnitiy refreshToken = refreshTokenService.createRefreshToken(user);
        return new UserLoginResponseDTO(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }
}
