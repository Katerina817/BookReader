package com.example.bookreader.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Entity
@Getter
@Table(name = "users")
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false,unique = true,length = 50)
    private String login;

    @Getter
    @Column(unique = true,length = 150)
    private String email;

    public User(){
        setRole(Role.USER);
    }

    public String getUsername() {
        return login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }
}
