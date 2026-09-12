package com.example.skbazaar.controller;

import com.example.skbazaar.model.entity.Order;
import com.example.skbazaar.model.enums.PaymentMethod;
import com.example.skbazaar.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam String deliveryAddress, @RequestParam PaymentMethod paymentMethod) {
        return orderService.checkout(deliveryAddress, paymentMethod);
    }

    @GetMapping("/my-orders")
    public List<Order> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
