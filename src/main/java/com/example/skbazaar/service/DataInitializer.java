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
        if (categoryRepository.count() > 0) {
            return;
        }

        // 1. Setup Admin & Seller
        User admin = userRepository.save(User.builder().name("SK Admin").email("admin@skbazaar.com").password(passwordEncoder.encode("admin123")).role(UserRole.ADMIN).build());
        User seller = userRepository.save(User.builder().name("SK Mega Merchant").email("seller@skbazaar.com").password(passwordEncoder.encode("seller123")).role(UserRole.SELLER).storeName("SK Official Store").walletBalance(new BigDecimal("100000.00")).build());

        // 2. Define Root Categories and their Sub-keywords/Images for Mocking
        createCategoryWithProducts("Fashion", seller, 50, 
            new String[]{"Shirt", "Jeans", "T-Shirt", "Saree", "Dress", "Kurta", "Jacket", "Suit"},
            new String[]{"Nike", "Levi's", "Adidas", "Zara", "H&M", "Louis Philippe", "FabIndia"},
            "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?q=80&w=500");

        createCategoryWithProducts("Electronics", seller, 50, 
            new String[]{"Phone", "Laptop", "Smartwatch", "Headphones", "Earbuds", "Monitor", "Tablet", "Camera"},
            new String[]{"Apple", "Samsung", "Sony", "Dell", "HP", "Asus", "OnePlus"},
            "https://images.unsplash.com/photo-1498049794561-7780e7231661?q=80&w=500");

        createCategoryWithProducts("Home & Kitchen", seller, 50, 
            new String[]{"Cookware", "Blender", "Air Fryer", "Curtains", "Bed", "Sofa", "Lamp", "Clock"},
            new String[]{"Prestige", "Philips", "IKEA", "Wakefit", "Bajaj", "Milton"},
            "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?q=80&w=500");

        createCategoryWithProducts("Beauty & Personal Care", seller, 50, 
            new String[]{"Face Wash", "Serum", "Lipstick", "Foundation", "Shampoo", "Soap", "Perfume"},
            new String[]{"Lakme", "Maybelline", "Mamaearth", "Nivea", "L'Oreal", "Garnier"},
            "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?q=80&w=500");

        createCategoryWithProducts("Grocery", seller, 50, 
            new String[]{"Rice", "Oil", "Dal", "Spices", "Dry Fruits", "Tea", "Coffee", "Snacks"},
            new String[]{"India Gate", "Fortune", "Tata", "Nescafe", "Catch", "Amul"},
            "https://images.unsplash.com/photo-1542838132-92c53300491e?q=80&w=500");

        createCategoryWithProducts("Sports & Fitness", seller, 50, 
            new String[]{"Bat", "Ball", "Yoga Mat", "Dumbbell", "Cycle", "Racket", "Jersey"},
            new String[]{"MRF", "Cosco", "Boldfit", "Decathlon", "Puma", "Reebok"},
            "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=500");

        createCategoryWithProducts("Books & Stationery", seller, 50,
            new String[]{"Novel", "Notebook", "Pen", "Dictionary", "Exam Guide", "Encyclopedia"},
            new String[]{"Penguin", "Classmate", "Parker", "Oxford", "HarperCollins"},
            "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?q=80&w=500");

        createCategoryWithProducts("Toys & Baby", seller, 50, 
            new String[]{"Car", "Doll", "Diaper", "Baby Food", "Puzzle", "Blocks", "Soft Toy"},
            new String[]{"Hot Wheels", "Barbie", "Pampers", "Lego", "Fisher-Price", "Johnson's"},
            "https://images.unsplash.com/photo-1532330393533-443990a51d10?q=80&w=500");

        createCategoryWithProducts("Automotive", seller, 50, 
            new String[]{"Car Wax", "Tyre Inflator", "Helmet", "Seat Cover", "Bike Lock", "Wiper"},
            new String[]{"3M", "GoMechanic", "Steelbird", "Studds", "Michelin"},
            "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?q=80&w=500");

        System.out.println("SK Bazaar: Full Seeded with 50 products per category (approx 450+ products total)");
    }

    private void createCategoryWithProducts(String catName, User seller, int count, String[] keywords, String[] brands, String baseImg) {
        Category category = categoryRepository.save(Category.builder().name(catName).build());
        List<Product> products = new ArrayList<>();
        
        for (int i = 1; i <= count; i++) {
            String keyword = keywords[random.nextInt(keywords.length)];
            String brand = brands[random.nextInt(brands.length)];
            String name = "Premium " + brand + " " + keyword + " #" + (1000 + i);
            
            int price = 200 + random.nextInt(5000);
            int mrp = price + (price / 2);
            
            products.add(Product.builder()
                .name(name)
                .brand(brand)
                .category(category)
                .seller(seller)
                .price(new BigDecimal(price))
                .mrp(new BigDecimal(mrp))
                .discount((double)Math.round(((double)(mrp-price)/mrp)*100))
                .rating(3.5 + random.nextDouble() * 1.5)
                .reviewCount(10 + random.nextInt(1000))
                .isFreeDelivery(price > 499)
                .stock(20 + random.nextInt(500))
                .shortDescription("High-end " + keyword + " from " + brand + ".")
                .description("Detailed description for " + name + ". This is a high-quality " + catName + " item.")
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .images(Arrays.asList(baseImg))
                .estimatedDelivery("2-4 Days")
                .countryOfOrigin("India")
                .manufacturer(brand + " Ltd.")
                .material("High Quality Material")
                .returnAvailable(true)
                .deliveryAvailable(true)
                .warehouse("Main SK Warehouse")
                .build());
        }
        productRepository.saveAll(products);
    }
}
