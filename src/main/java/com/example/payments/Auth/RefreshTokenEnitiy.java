package com.example.payments.Auth;

import com.example.payments.Users.Entity.UserEnitiy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class RefreshTokenEnitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenId;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private UserEnitiy user;

    @Column(
            unique = true,
            nullable = false
    )
    private String token;
    @Column(
            nullable = false
    )
    private Instant expiration;
}
