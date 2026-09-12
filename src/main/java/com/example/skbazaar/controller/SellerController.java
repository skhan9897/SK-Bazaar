package com.example.skbazaar.controller;

import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.repository.CategoryRepository;
import com.example.skbazaar.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(sellerService.getSellerDashboardStats());
        return "seller-dashboard";
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        model.addAttribute("products", sellerService.getSellerInventory());
        return "seller-inventory";
    }

    @GetMapping("/products/add")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "seller-add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        sellerService.addProduct(product);
        return "redirect:/seller/inventory?success=true";
    }
}
