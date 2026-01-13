package com.app.service;

import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);

    AuthResponse refreshToken(String refreshToken);
}
