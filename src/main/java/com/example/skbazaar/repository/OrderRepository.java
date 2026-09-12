package com.example.skbazaar.repository;

import com.example.skbazaar.model.entity.Order;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User customer);
    List<Order> findByDeliveryPartner(User deliveryPartner);
    List<Order> findByStatus(OrderStatus status);
}
