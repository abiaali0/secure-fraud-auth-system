package com.abia.fraudauth.auth;

import com.abia.fraudauth.audit.AuditService;
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
    private final AuditService audit;

    public AuthService(
        UserRepository users,
        PasswordEncoder encoder,
        JwtService jwt,
        AuditService audit
    ) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
        this.audit = audit;
    }

    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        String email = request.email().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(request.password()));
        user.setRole(Role.USER);
        users.save(user);
        audit.record(email, "USER_REGISTERED", "New user account created");

        return new AuthDtos.AuthResponse(
            jwt.createToken(user.getEmail(), user.getRole().name()),
            user.getEmail()
        );
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        String email = request.email().toLowerCase();
        User user = users.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        audit.record(email, "USER_LOGIN", "Successful login");
        return new AuthDtos.AuthResponse(
            jwt.createToken(user.getEmail(), user.getRole().name()),
            user.getEmail()
        );
    }
}
