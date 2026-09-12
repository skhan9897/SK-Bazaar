package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Category;
import com.example.skbazaar.model.entity.Product;
import com.example.skbazaar.model.entity.ProductVariant;
import com.example.skbazaar.model.enums.UserRole;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.CategoryRepository;
import com.example.skbazaar.repository.ProductRepository;
import com.example.skbazaar.repository.ProductVariantRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            return; // Data already exists
        }

        // 1. Create Admin & Seller
        User admin = User.builder()
                .name("SK Admin")
                .email("admin@skbazaar.com")
                .password(passwordEncoder.encode("admin123"))
                .role(UserRole.ADMIN)
                .build();
        userRepository.save(admin);

        User seller = User.builder()
                .name("Global Electronics")
                .email("seller@skbazaar.com")
                .password(passwordEncoder.encode("seller123"))
                .role(UserRole.SELLER)
                .storeName("SK Mega Store")
                .build();
        userRepository.save(seller);

        // 2. Create Categories Hierarchy as per Image
        Category fashion = categoryRepository.save(Category.builder().name("Fashion").build());
        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        Category homeKitchen = categoryRepository.save(Category.builder().name("Home & Kitchen").build());
        Category beauty = categoryRepository.save(Category.builder().name("Beauty & Personal Care").build());
        Category grocery = categoryRepository.save(Category.builder().name("Grocery").build());
        Category sports = categoryRepository.save(Category.builder().name("Sports & Fitness").build());
        Category books = categoryRepository.save(Category.builder().name("Books & Stationery").build());
        Category toys = categoryRepository.save(Category.builder().name("Toys & Baby").build());
        Category automotive = categoryRepository.save(Category.builder().name("Automotive").build());
        Category health = categoryRepository.save(Category.builder().name("Health & Wellness").build());
        Category shoes = categoryRepository.save(Category.builder().name("Shoes & Footwear").build());
        Category jewellery = categoryRepository.save(Category.builder().name("Jewellery & Accessories").build());
        Category pets = categoryRepository.save(Category.builder().name("Pet Supplies").build());
        Category tools = categoryRepository.save(Category.builder().name("Tools & Hardware").build());

        // Subcategories for Fashion
        categoryRepository.save(Category.builder().name("Men").parentCategory(fashion).build());
        categoryRepository.save(Category.builder().name("Women").parentCategory(fashion).build());
        categoryRepository.save(Category.builder().name("Kids").parentCategory(fashion).build());

        // 3. Create Products for different sections as per Image requirements
        
        // Flash Sale Item
        Product p1 = productRepository.save(Product.builder()
                .name("Samsung Galaxy Buds2 Pro")
                .brand("Samsung")
                .price(new BigDecimal("1299"))
                .mrp(new BigDecimal("2499"))
                .discount(48.0)
                .rating(4.4)
                .reviewCount(1520)
                .isFreeDelivery(true)
                .stock(50)
                .description("Noise cancelling earbuds.")
                .category(electronics)
                .seller(seller)
                .build());

        // Recommended / Trending
        productRepository.save(Product.builder()
                .name("Nike Air Max")
                .brand("Nike")
                .price(new BigDecimal("8999"))
                .mrp(new BigDecimal("12999"))
                .discount(30.0)
                .rating(4.8)
                .isFreeDelivery(true)
                .stock(100)
                .category(shoes)
                .seller(seller)
                .build());

        // Under 499 Item
        productRepository.save(Product.builder()
                .name("Handmade Coffee Mug")
                .brand("HomeDeco")
                .price(new BigDecimal("299"))
                .mrp(new BigDecimal("599"))
                .discount(50.0)
                .rating(4.2)
                .isFreeDelivery(false)
                .stock(500)
                .category(homeKitchen)
                .seller(seller)
                .build());

        // Fashion Deal
        productRepository.save(Product.builder()
                .name("Premium Silk Saree")
                .brand("Ethnix")
                .price(new BigDecimal("2499"))
                .mrp(new BigDecimal("4999"))
                .discount(50.0)
                .rating(4.5)
                .isFreeDelivery(true)
                .stock(200)
                .category(fashion)
                .seller(seller)
                .build());

        // 4. Add Variants to iPhone
        variantRepository.save(ProductVariant.builder()
                .product(p1).color("Natural Titanium").size("256GB").stock(20).price(new BigDecimal("144900")).build());
        variantRepository.save(ProductVariant.builder()
                .product(p1).color("Blue Titanium").size("512GB").stock(10).price(new BigDecimal("164900")).build());

        System.out.println("SK Bazaar Sample Data Seeded Successfully!");
    }
}
