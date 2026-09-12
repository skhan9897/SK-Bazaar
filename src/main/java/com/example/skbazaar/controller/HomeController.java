package com.example.skbazaar.controller;

import com.example.skbazaar.constants.AppConstants;
import com.example.skbazaar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", AppConstants.APP_NAME);
        model.addAttribute("tagline", AppConstants.TAGLINE);
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }
}
