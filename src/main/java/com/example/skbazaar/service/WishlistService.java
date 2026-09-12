package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.model.entity.Wishlist;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.UserRepository;
import com.example.skbazaar.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<Wishlist> getMyWishlist() {
        return wishlistRepository.findByCustomer(getCurrentUser());
    }

    public Wishlist addToWishlist(Long productId) {
        User user = getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow();
        Wishlist wishlist = Wishlist.builder()
                .customer(user)
                .product(product)
                .build();
        return wishlistRepository.save(wishlist);
    }
}
