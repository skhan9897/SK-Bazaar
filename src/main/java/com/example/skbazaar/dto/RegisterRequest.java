package com.example.skbazaar.dto;

import com.example.skbazaar.model.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private UserRole role;
}
