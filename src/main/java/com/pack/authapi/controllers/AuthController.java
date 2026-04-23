package com.pack.authapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pack.authapi.dtos.AuthRequest;
import com.pack.authapi.dtos.AuthResponse;
import com.pack.authapi.dtos.RegisterRequest;
import com.pack.authapi.models.UserEntity;
import com.pack.authapi.repositories.UserRepository;
import com.pack.authapi.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login-user")
    public ResponseEntity<AuthResponse> logIn(@RequestBody AuthRequest request) {
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }
}
