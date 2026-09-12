package com.example.skbazaar.service;

import com.example.skbazaar.dto.AuthRequest;
import com.example.skbazaar.dto.AuthResponse;
import com.example.skbazaar.dto.RegisterRequest;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.UserRepository;
import com.example.skbazaar.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final OtpCodeRepository otpCodeRepository;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .walletBalance(BigDecimal.ZERO)
                .build();
        userRepository.save(user);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        var jwtToken = jwtUtils.generateToken(userDetails);
        return new AuthResponse(jwtToken, user.getEmail(), user.getRole().name());
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        var jwtToken = jwtUtils.generateToken(userDetails);
        return new AuthResponse(jwtToken, user.getEmail(), user.getRole().name());
    }

    public void sendOtp(String mobile) {
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6 digit OTP
        otpCodeRepository.deleteByMobile(mobile);
        var otpCode = OtpCode.builder()
                .mobile(mobile)
                .code(otp)
                .expiryTime(java.time.LocalDateTime.now().plusMinutes(5))
                .build();
        otpCodeRepository.save(otpCode);
        System.out.println("OTP for " + mobile + " is: " + otp); // Simulating SMS
    }

    public AuthResponse verifyOtp(String mobile, String code) {
        var otpCode = otpCodeRepository.findByMobileAndCode(mobile, code)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (otpCode.getExpiryTime().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("OTP Expired");
        }

        var user = userRepository.findByMobile(mobile)
                .orElseGet(() -> {
                    // Create new user if not exists for mobile login
                    var newUser = User.builder()
                            .mobile(mobile)
                            .role(com.example.skbazaar.model.enums.UserRole.CUSTOMER)
                            .walletBalance(BigDecimal.ZERO)
                            .build();
                    return userRepository.save(newUser);
                });

        otpCodeRepository.deleteByMobile(mobile);
        
        // Handling dummy user details for JWT
        String username = user.getEmail() != null ? user.getEmail() : user.getMobile();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("") // No password for OTP login
                .authorities("ROLE_" + user.getRole().name())
                .build();
                
        var jwtToken = jwtUtils.generateToken(userDetails);
        return new AuthResponse(jwtToken, username, user.getRole().name());
    }
}
