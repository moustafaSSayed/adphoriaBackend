package com.app.service.impl;

import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.RegisterRequest;
import com.app.entity.User;
import com.app.exception.BadRequestException;
import com.app.repository.UserRepository;
import com.app.security.JwtUtil;
import com.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role("USER")
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Store refresh token in database
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(1800L) // 30 minutes in seconds
                .username(user.getUsername())
                .message("User registered successfully")
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Store refresh token in database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(1800L) // 30 minutes in seconds
                .username(request.getUsername())
                .message("Login successful")
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            // Extract username from refresh token
            String username = jwtUtil.extractUsername(refreshToken);

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate refresh token
            if (!jwtUtil.validateRefreshToken(refreshToken, userDetails)) {
                throw new BadRequestException("Invalid refresh token");
            }

            // Verify token matches the one stored in database
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException("User not found"));

            if (!refreshToken.equals(user.getRefreshToken())) {
                throw new BadRequestException("Refresh token does not match");
            }

            // Generate new access token
            String newAccessToken = jwtUtil.generateToken(userDetails);

            // Optionally generate new refresh token (token rotation)
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);

            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .expiresIn(1800L) // 30 minutes in seconds
                    .username(username)
                    .message("Token refreshed successfully")
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("Invalid or expired refresh token: " + e.getMessage());
        }
    }
}
