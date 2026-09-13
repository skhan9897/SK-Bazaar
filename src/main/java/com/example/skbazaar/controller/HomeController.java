package com.example.skbazaar.controller;

import com.example.skbazaar.constants.AppConstants;
import com.example.skbazaar.dto.RegisterRequest;
import com.example.skbazaar.model.entity.Address;
import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.enums.PaymentMethod;
import com.example.skbazaar.model.enums.UserRole;
import com.example.skbazaar.repository.CategoryRepository;
import com.example.skbazaar.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final CartService cartService;
    private final WishlistService wishlistService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final DashboardService dashboardService;
    private final AuthService authService;

    @GetMapping("/")
    public String splashPage() {
        return "splash";
    }

    @GetMapping("/home-dashboard")
    public String home(Model model) {
        model.addAttribute("appName", AppConstants.APP_NAME);
        model.addAttribute("tagline", AppConstants.TAGLINE);
        
        try {
            // Fetch root categories for the main category menu
            model.addAttribute("rootCategories", categoryRepository.findByParentCategoryIsNull());

            // Amazon/Flipkart Style Marketplace Sections logic - ONLY SHOW APPROVED PRODUCTS
            var productsInDb = productService.getAllProducts();
            
            if (productsInDb == null) {
                productsInDb = java.util.Collections.emptyList();
            }

            var allProducts = productsInDb.stream()
                    .filter(p -> p != null && p.getStatus() != null && p.getStatus().name().equals("APPROVED"))
                    .toList();
            
            model.addAttribute("flashSale", allProducts.stream().filter(p -> p.getDiscount() != null && p.getDiscount() > 20).limit(4).toList());
            model.addAttribute("trendingProducts", allProducts.stream().limit(8).toList());
            model.addAttribute("recommendedProducts", allProducts.stream().skip(allProducts.size() > 2 ? 2 : 0).limit(4).toList());
            model.addAttribute("bestSellers", allProducts.stream().limit(4).toList());
            
            model.addAttribute("electronicsDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Electronics".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            model.addAttribute("fashionDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Fashion".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            model.addAttribute("homeKitchenDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Home & Kitchen".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            model.addAttribute("beautyDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Beauty & Personal Care".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            model.addAttribute("groceryDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Grocery".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            model.addAttribute("sportsDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Sports & Fitness".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
            
            model.addAttribute("dealsUnder499", allProducts.stream().filter(p -> p.getPrice() != null && p.getPrice().doubleValue() < 499).limit(4).toList());
            model.addAttribute("recentlyAdded", allProducts.stream().limit(4).toList()); // Simplified sorting to avoid ID comparison issues

        } catch (Exception e) {
            // Fallback for DB/Processing errors
            model.addAttribute("flashSale", java.util.Collections.emptyList());
            model.addAttribute("trendingProducts", java.util.Collections.emptyList());
            model.addAttribute("recommendedProducts", java.util.Collections.emptyList());
            model.addAttribute("bestSellers", java.util.Collections.emptyList());
            model.addAttribute("electronicsDeals", java.util.Collections.emptyList());
            model.addAttribute("fashionDeals", java.util.Collections.emptyList());
            model.addAttribute("homeKitchenDeals", java.util.Collections.emptyList());
            model.addAttribute("beautyDeals", java.util.Collections.emptyList());
            model.addAttribute("groceryDeals", java.util.Collections.emptyList());
            model.addAttribute("sportsDeals", java.util.Collections.emptyList());
            model.addAttribute("dealsUnder499", java.util.Collections.emptyList());
            model.addAttribute("recentlyAdded", java.util.Collections.emptyList());
        }

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(UserRole.CUSTOMER);
        }
        authService.register(request);
        return "redirect:/login?registered=true";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String q, Model model) {
        model.addAttribute("query", q);
        model.addAttribute("products", productService.searchProducts(q));
        model.addAttribute("rootCategories", categoryRepository.findByParentCategoryIsNull());
        model.addAttribute("appName", AppConstants.APP_NAME);
        return "home";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCart());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/wishlist")
    public String viewWishlist(Model model) {
        model.addAttribute("wishlistItems", wishlistService.getMyWishlist());
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addToWishlist(@RequestParam Long productId) {
        wishlistService.addToWishlist(productId);
        return "redirect:/wishlist";
    }

    @GetMapping("/address")
    public String viewAddresses(Model model) {
        model.addAttribute("addresses", addressService.getMyAddresses());
        model.addAttribute("newAddress", new Address());
        return "address";
    }

    @PostMapping("/address/add")
    public String addAddress(@ModelAttribute Address address) {
        address.setIsDefault(true);
        addressService.addAddress(address);
        return "redirect:/address";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        var items = cartService.getCart();
        java.math.BigDecimal total = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("cartItems", items);
        model.addAttribute("totalAmount", total);
        model.addAttribute("addresses", addressService.getMyAddresses());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "checkout";
    }

    @PostMapping("/checkout/place")
    public String placeOrder(@RequestParam String deliveryAddress, @RequestParam PaymentMethod paymentMethod) {
        orderService.checkout(deliveryAddress, paymentMethod);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getMyOrders());
        return "orders";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        var user = authService.getUserByEmail(email);
        model.addAttribute("user", user);
        
        // Add recommended products to the dashboard
        var allProducts = productService.getAllProducts().stream()
                .filter(p -> p != null && p.getStatus() != null && p.getStatus().name().equals("APPROVED"))
                .limit(4)
                .toList();
        model.addAttribute("recommendedProducts", allProducts);

        return "customer-dashboard";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        var user = authService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAllAttributes(dashboardService.getAdminDashboardStats());
        
        var allProducts = productService.getAllProducts();
        
        // For product approval
        model.addAttribute("pendingProducts", allProducts.stream()
                .filter(p -> p != null && p.getStatus() != null && p.getStatus().name().equals("PENDING")).toList());
        
        // For inventory management
        model.addAttribute("allProducts", allProducts);

        return "admin-dashboard";
    }

    @PostMapping("/admin/products/approve/{id}")
    public String approveProduct(@PathVariable Long id) {
        var product = productService.getProductById(id);
        product.setStatus(com.example.skbazaar.model.enums.ProductStatus.APPROVED);
        productService.saveProduct(product);
        return "redirect:/admin/dashboard?approved=true";
    }
}
