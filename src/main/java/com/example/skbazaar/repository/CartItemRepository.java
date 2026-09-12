package com.example.skbazaar.repository;

import com.example.skbazaar.model.entity.CartItem;
import com.example.skbazaar.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomer(User customer);
    Optional<CartItem> findByCustomerAndProduct_Id(User customer, Long productId);
    void deleteByCustomer(User customer);
}
