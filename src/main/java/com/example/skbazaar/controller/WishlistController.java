package com.example.skbazaar.controller;

import com.example.skbazaar.model.entity.Wishlist;
import com.example.skbazaar.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public List<Wishlist> getMyWishlist() {
        return wishlistService.getMyWishlist();
    }

    @PostMapping("/add/{productId}")
    public Wishlist addToWishlist(@PathVariable Long productId) {
        return wishlistService.addToWishlist(productId);
    }
}
