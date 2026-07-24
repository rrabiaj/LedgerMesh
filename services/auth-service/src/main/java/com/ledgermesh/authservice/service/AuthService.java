package com.ledgermesh.authservice.service;

import com.ledgermesh.authservice.dto.AuthResponseDTO;
import com.ledgermesh.authservice.dto.LoginRequestDTO;
import com.ledgermesh.authservice.dto.RegisterRequestDTO;
import com.ledgermesh.authservice.model.Role;
import com.ledgermesh.authservice.model.User;
import com.ledgermesh.authservice.repository.UserRepository;
import com.ledgermesh.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @SuppressWarnings("null")
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        
        User saved = userRepository.save(user);
        String jwtToken = jwtService.generateToken(saved);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .userID(saved.getId())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

        String jwtToken = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .userID(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}