package com.abia.fraudauth.auth;

import com.abia.fraudauth.user.Role;
import com.abia.fraudauth.user.User;
import com.abia.fraudauth.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        if (users.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.email().toLowerCase());
        user.setPasswordHash(encoder.encode(request.password()));
        user.setRole(Role.USER);
        users.save(user);

        return new AuthDtos.AuthResponse(
            jwt.createToken(user.getEmail(), user.getRole().name()),
            user.getEmail()
        );
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        User user = users.findByEmail(request.email().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new AuthDtos.AuthResponse(
            jwt.createToken(user.getEmail(), user.getRole().name()),
            user.getEmail()
        );
    }
}
