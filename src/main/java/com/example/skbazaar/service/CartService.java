package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.CartItem;
import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.CartItemRepository;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<CartItem> getCart() {
        return cartItemRepository.findByCustomer(getCurrentUser());
    }

    public CartItem addToCart(Long productId, Integer quantity) {
        User user = getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow();
        
        return cartItemRepository.findByCustomerAndProduct_Id(user, productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .customer(user)
                            .product(product)
                            .quantity(quantity)
                            .build();
                    return cartItemRepository.save(newItem);
                });
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
