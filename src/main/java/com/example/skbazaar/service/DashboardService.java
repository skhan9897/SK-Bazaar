package com.example.skbazaar.service;

import com.example.skbazaar.model.enums.UserRole;
import com.example.skbazaar.repository.OrderRepository;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCustomers", userRepository.findByRole(UserRole.CUSTOMER).size());
        stats.put("totalSellers", userRepository.findByRole(UserRole.SELLER).size());
        stats.put("totalProducts", productRepository.count());
        stats.put("totalOrders", orderRepository.count());
        // Add more complex logic for Today's revenue, pending orders, etc.
        return stats;
    }
}
