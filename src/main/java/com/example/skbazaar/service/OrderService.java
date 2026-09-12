package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.*;
import com.example.skbazaar.model.enums.OrderStatus;
import com.example.skbazaar.model.enums.PaymentMethod;
import com.example.skbazaar.repository.CartItemRepository;
import com.example.skbazaar.repository.OrderRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public Order checkout(String deliveryAddress, PaymentMethod paymentMethod) {
        User user = getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findByCustomer(user);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .customer(user)
                .totalAmount(totalAmount)
                .discountAmount(BigDecimal.ZERO)
                .payableAmount(totalAmount)
                .status(OrderStatus.ORDER_PLACED)
                .paymentMethod(paymentMethod)
                .deliveryAddress(deliveryAddress)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> OrderItem.builder()
                        .order(order)
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build())
                .collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);

        cartItemRepository.deleteByCustomer(user);
        
        return order;
    }

    public List<Order> getMyOrders() {
        return orderRepository.findByCustomer(getCurrentUser());
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}
