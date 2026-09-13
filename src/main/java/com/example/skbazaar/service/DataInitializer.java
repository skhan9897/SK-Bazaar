package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Category;
import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.enums.UserRole;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.CategoryRepository;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        // ALWAYS SEED ON STARTUP FOR TESTING (Updating purana data)
        if (productRepository.count() > 400) {
            return;
        }

        // 1. Users
        userRepository.deleteAll();
        User admin = userRepository.save(User.builder().name("SK Admin").email("admin@skbazaar.com").password(passwordEncoder.encode("admin123")).role(UserRole.ADMIN).build());
        User seller = userRepository.save(User.builder().name("SK Merchant").email("seller@skbazaar.com").password(passwordEncoder.encode("seller123")).role(UserRole.SELLER).storeName("SK Official Store").walletBalance(new BigDecimal("100000.00")).build());

        // 2. Categories
        categoryRepository.deleteAll();
        seedCategory("Fashion", seller, new String[]{"Shirt", "Jeans", "T-Shirt", "Saree", "Dress", "Kurta"}, "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?w=500");
        seedCategory("Electronics", seller, new String[]{"Phone", "Laptop", "Smartwatch", "Headphones"}, "https://images.unsplash.com/photo-1498049794561-7780e7231661?w=500");
        seedCategory("Home", seller, new String[]{"Bed", "Sofa", "Table", "Lamp", "Clock"}, "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=500");
        seedCategory("Grocery", seller, new String[]{"Rice", "Oil", "Dal", "Dry Fruits"}, "https://images.unsplash.com/photo-1542838132-92c53300491e?w=500");
        seedCategory("Mobiles", seller, new String[]{"iPhone", "Pixel", "OnePlus", "Galaxy"}, "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500");
        seedCategory("Appliances", seller, new String[]{"Mixer", "Oven", "Iron", "Heater"}, "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=500");
        seedCategory("Toys", seller, new String[]{"Car", "Puzzle", "Lego", "Doll"}, "https://images.unsplash.com/photo-1532330393533-443990a51d10?w=500");

        System.out.println("SK Bazaar: FLIPKART ARCHITECTURE SEEDED WITH 400+ PRODUCTS");
    }

    private void seedCategory(String name, User seller, String[] keywords, String img) {
        Category cat = categoryRepository.save(Category.builder().name(name).build());
        List<Product> products = new ArrayList<>();
        String[] brands = {"Nike", "Apple", "Samsung", "Louis Philippe", "Prestige", "Fortune", "Lego"};
        
        for (int i = 0; i < 50; i++) {
            int price = 300 + random.nextInt(5000);
            products.add(Product.builder()
                .name(brands[random.nextInt(brands.length)] + " " + keywords[random.nextInt(keywords.length)] + " #" + (100+i))
                .brand(brands[random.nextInt(brands.length)])
                .category(cat).seller(seller).price(new BigDecimal(price)).mrp(new BigDecimal(price + 500))
                .discount((double)random.nextInt(50)).rating(3.5 + random.nextDouble() * 1.5).reviewCount(10 + random.nextInt(1000))
                .stock(100).isFreeDelivery(true).status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .images(Arrays.asList(img)).shortDescription("Best quality " + name).description("Professional product.")
                .build());
        }
        productRepository.saveAll(products);
    }
}
