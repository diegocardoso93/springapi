package com.projeto.springapi.security;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private String refreshToken;

    public LoginResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
