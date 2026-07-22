package com.makar.authservice.service;

import com.makar.authservice.dto.LoginRequestDTO;
import com.makar.authservice.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticateLogin(LoginRequestDTO loginRequestDTO){
        Optional<String> token =
                userService.findByEmail(loginRequestDTO.userEmail())
                        .filter(u-> passwordEncoder.matches(loginRequestDTO.userPassword(),u.getUserPassword()))
                        .map(u->jwtUtil.generateToken(u.getUserEmail(),u.getUserRole()));


        return token;
    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
}
