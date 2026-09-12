package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<Product> searchProducts(String query) {
        // Simple search by name for now
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
