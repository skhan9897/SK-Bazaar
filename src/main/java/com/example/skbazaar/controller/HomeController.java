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
    public String home(Model model) {
        model.addAttribute("appName", AppConstants.APP_NAME);
        model.addAttribute("tagline", AppConstants.TAGLINE);
        
        // Amazon/Flipkart Style Marketplace Sections logic
        var allProducts = productService.getAllProducts();
        
        model.addAttribute("flashSale", allProducts.stream().filter(p -> p.getDiscount() != null && p.getDiscount() > 20).limit(4).toList());
        model.addAttribute("trendingProducts", allProducts.stream().limit(8).toList());
        model.addAttribute("recommendedProducts", allProducts.stream().skip(2).limit(4).toList());
        model.addAttribute("bestSellers", allProducts.stream().limit(4).toList());
        
        model.addAttribute("electronicsDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Electronics".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
        model.addAttribute("fashionDeals", allProducts.stream().filter(p -> p.getCategory() != null && "Fashion".equalsIgnoreCase(p.getCategory().getName())).limit(4).toList());
        model.addAttribute("dealsUnder499", allProducts.stream().filter(p -> p.getPrice().doubleValue() < 499).limit(4).toList());
        
        model.addAttribute("recentlyAdded", allProducts.stream().sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).limit(4).toList());

        model.addAttribute("categories", categoryRepository.findAll());
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

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAllAttributes(dashboardService.getAdminDashboardStats());
        return "admin-dashboard";
    }
}
