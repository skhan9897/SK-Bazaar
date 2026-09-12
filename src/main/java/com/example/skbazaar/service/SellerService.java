package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Order;
import com.example.skbazaar.model.entity.OrderItem;
import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.OrderRepository;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private User getCurrentSeller() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Map<String, Object> getSellerDashboardStats() {
        User seller = getCurrentSeller();
        List<Product> products = productRepository.findBySeller(seller);
        
        // Complex logic for earnings (Total sales from items belonging to this seller)
        List<Order> allOrders = orderRepository.findAll();
        List<OrderItem> sellerItems = allOrders.stream()
                .flatMap(o -> o.getItems().stream())
                .filter(item -> item.getProduct().getSeller().getId().equals(seller.getId()))
                .collect(Collectors.toList());

        BigDecimal totalEarnings = sellerItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate 10% Platform Commission
        BigDecimal commission = totalEarnings.multiply(new BigDecimal("0.10"));
        BigDecimal netPayable = totalEarnings.subtract(commission);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", products.size());
        stats.put("totalOrders", sellerItems.size());
        stats.put("totalEarnings", totalEarnings);
        stats.put("commission", commission);
        stats.put("netPayable", netPayable);
        stats.put("products", products);
        
        return stats;
    }

    public List<Product> getSellerInventory() {
        return productRepository.findBySeller(getCurrentSeller());
    }

    public Product addProduct(Product product) {
        product.setSeller(getCurrentSeller());
        product.setStatus(com.example.skbazaar.model.enums.ProductStatus.PENDING); // Admin must approve
        return productRepository.save(product);
    }
}
