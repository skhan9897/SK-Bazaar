package com.example.skbazaar.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String mobile; // For OTP login later
}
