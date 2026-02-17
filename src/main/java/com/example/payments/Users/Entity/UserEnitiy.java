package com.example.payments.Users.Entity;

import com.example.payments.Users.Roles.Roles;
import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class UserEnitiy implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(
            name = "f_name"
    )
    private String userFirstName;
    @Column(
            name = "l_name"
    )
    private String userLastName;
    @Column(
            name = "email"
    )
    private String userEmail; ;
    @Column(
            name = "phone_number"
    )
    private String userPhoneNumber;
    @Column(
            name = "password"
    )
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Roles userRoles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRoles.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
