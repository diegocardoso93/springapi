package com.projeto.springapi.controller;

import com.projeto.springapi.security.JwtTokenProvider;
import com.projeto.springapi.service.UserDetailsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

import com.projeto.springapi.security.LoginRequest;
import com.projeto.springapi.security.LoginResponse;
import com.projeto.springapi.security.RefreshTokenRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, refreshToken));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Renova o token JWT")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newToken = jwtTokenProvider.generateToken(userDetails);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(newToken, newRefreshToken));
        }
        return ResponseEntity.status(400).body("Refresh token inválido ou expirado");
    }
}
