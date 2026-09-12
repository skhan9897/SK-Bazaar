package com.example.skbazaar.model.entity;

import com.example.skbazaar.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // For Customer wallet
    private BigDecimal walletBalance = BigDecimal.ZERO;

    // For Seller store info
    private String storeName;
    private String storeAddress;

    // For Delivery partner info
    private String vehicleNumber;
    private String vehicleType;
}
