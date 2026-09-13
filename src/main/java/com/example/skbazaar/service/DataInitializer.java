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
import java.util.ArrayList;
import java.util.Arrays;
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
                .name("SK Mega Merchant")
                .email("seller@skbazaar.com")
                .password(passwordEncoder.encode("seller123"))
                .role(UserRole.SELLER)
                .storeName("SK Official Store")
                .walletBalance(new BigDecimal("5000.00"))
                .build();
        userRepository.save(seller);

        // 2. Create Categories Hierarchy (Root Level)
        Category fashion = categoryRepository.save(Category.builder().name("Fashion").build());
        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        Category homeKitchen = categoryRepository.save(Category.builder().name("Home & Kitchen").build());
        Category beauty = categoryRepository.save(Category.builder().name("Beauty & Personal Care").build());
        Category grocery = categoryRepository.save(Category.builder().name("Grocery & Food").build());
        Category furniture = categoryRepository.save(Category.builder().name("Furniture & Office").build());
        Category books = categoryRepository.save(Category.builder().name("Books & Education").build());
        Category sports = categoryRepository.save(Category.builder().name("Sports & Fitness").build());
        Category toys = categoryRepository.save(Category.builder().name("Toys & Baby").build());
        Category automotive = categoryRepository.save(Category.builder().name("Automotive").build());
        Category pets = categoryRepository.save(Category.builder().name("Pet Supplies").build());
        Category tools = categoryRepository.save(Category.builder().name("Tools & Hardware").build());
        Category gifts = categoryRepository.save(Category.builder().name("Gifts & Others").build());

        // Sub-categories for mapping
        Category men = categoryRepository.save(Category.builder().name("Men").parentCategory(fashion).build());
        Category mobiles = categoryRepository.save(Category.builder().name("Mobiles").parentCategory(electronics).build());
        Category kitchen = categoryRepository.save(Category.builder().name("Kitchen").parentCategory(homeKitchen).build());

        // 3. Create 20 Premium Products across categories
        List<Product> sampleProducts = new ArrayList<>();

        // Electronics - Mobiles
        sampleProducts.add(createProduct("iPhone 15 Pro", "Apple", mobiles, seller, "119900", "134900", "Titanium body, A17 Pro chip.", "https://images.unsplash.com/photo-1695048133142-1a20484d2569?q=80&w=500"));
        sampleProducts.add(createProduct("Samsung S24 Ultra", "Samsung", mobiles, seller, "124999", "139999", "Galaxy AI, 200MP Camera.", "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?q=80&w=500"));

        // Fashion - Men
        Category tshirts = categoryRepository.save(Category.builder().name("T-Shirts").parentCategory(men).build());
        sampleProducts.add(createProduct("Premium Polo T-Shirt", "U.S. Polo Assn.", tshirts, seller, "1299", "2499", "100% Pure Pique Cotton.", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=500"));
        sampleProducts.add(createProduct("Slim Fit Denim Jeans", "Levi's", men, seller, "2999", "4599", "Classic blue wash stretchable denim.", "https://images.unsplash.com/photo-1542272604-787c3835535d?q=80&w=500"));

        // Home & Kitchen
        Category cookware = categoryRepository.save(Category.builder().name("Cookware").parentCategory(kitchen).build());
        sampleProducts.add(createProduct("7-Piece Non-Stick Set", "Prestige", cookware, seller, "3499", "5999", "Hard anodized induction base.", "https://images.unsplash.com/photo-1584990344321-27682ad0f144?q=80&w=500"));
        sampleProducts.add(createProduct("Modern Coffee Table", "Wakefit", homeKitchen, seller, "4999", "8999", "Sheesham wood finish table.", "https://images.unsplash.com/photo-1533090481720-856c6e3c1fdc?q=80&w=500"));

        // Beauty
        Category skincare = categoryRepository.save(Category.builder().name("Skincare").parentCategory(beauty).build());
        sampleProducts.add(createProduct("Vitamin C Face Serum", "Mamaearth", skincare, seller, "599", "899", "Brightens skin & reduces spots.", "https://images.unsplash.com/photo-1620916566398-39f1143af7be?q=80&w=500"));
        sampleProducts.add(createProduct("Matte Red Lipstick", "Lakme", beauty, seller, "450", "750", "Long-lasting 12hr stay.", "https://images.unsplash.com/photo-1586495777744-4413f21062fa?q=80&w=500"));

        // Grocery
        sampleProducts.add(createProduct("Organic Basmati Rice 5kg", "India Gate", grocery, seller, "749", "999", "Aged long grain aromatic rice.", "https://images.unsplash.com/photo-1586201375761-83865001e31c?q=80&w=500"));
        sampleProducts.add(createProduct("Cold Pressed Mustard Oil", "Fortune", grocery, seller, "199", "250", "100% pure Kachi Ghani oil.", "https://images.unsplash.com/photo-1474979266404-7eaacccbc7c5?q=80&w=500"));

        // Furniture
        sampleProducts.add(createProduct("Ergonomic Office Chair", "Green Soul", furniture, seller, "8999", "14999", "High back mesh with lumbar support.", "https://images.unsplash.com/photo-1505797149-43b0069ec26b?q=80&w=500"));

        // Books
        sampleProducts.add(createProduct("Atomic Habits", "James Clear", books, seller, "499", "799", "Self-help bestseller on habits.", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=500"));

        // Sports
        sampleProducts.add(createProduct("Cricket Bat (Kashmir Willow)", "MRF", sports, seller, "2499", "3999", "Professional grade power willow.", "https://images.unsplash.com/photo-1531415074968-036ba1b575da?q=80&w=500"));
        sampleProducts.add(createProduct("Yoga Mat (6mm)", "Boldfit", sports, seller, "799", "1499", "Anti-skid double layered mat.", "https://images.unsplash.com/photo-1592432678016-e910b452f9a2?q=80&w=500"));

        // Toys
        sampleProducts.add(createProduct("Remote Control Monster Truck", "Hot Wheels", toys, seller, "1599", "2999", "4WD off-road climbing car.", "https://images.unsplash.com/photo-1594736797933-d0501ba2fe65?q=80&w=500"));

        // Automotive
        sampleProducts.add(createProduct("Digital Tyre Inflator", "GoMechanic", automotive, seller, "1899", "3499", "Fast inflation with auto cut-off.", "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?q=80&w=500"));

        // Pets
        sampleProducts.add(createProduct("Adult Dog Food 3kg", "Pedigree", pets, seller, "650", "850", "Chicken & Vegetables flavor.", "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?q=80&w=500"));

        // Tools
        sampleProducts.add(createProduct("21V Cordless Drill Machine", "Bosch", tools, seller, "4599", "7999", "Variable speed with 2 batteries.", "https://images.unsplash.com/photo-1504148455328-c376907d081c?q=80&w=500"));

        // Gifts
        sampleProducts.add(createProduct("Personalized Photo Frame", "GiftingEra", gifts, seller, "399", "699", "A4 size wooden floating frame.", "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?q=80&w=500"));

        // One more Electronics item to make it 20
        sampleProducts.add(createProduct("Wireless Noise Cancelling Headphones", "Sony", electronics, seller, "19990", "29990", "XM5 Series with industry leading ANC.", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?q=80&w=500"));

        productRepository.saveAll(sampleProducts);

        System.out.println("SK Bazaar: 20 Premium Products across all categories seeded successfully!");
    }

    private Product createProduct(String name, String brand, Category category, User seller, String price, String mrp, String shortDesc, String imageUrl) {
        BigDecimal p = new BigDecimal(price);
        BigDecimal m = new BigDecimal(mrp);
        double disc = ((m.doubleValue() - p.doubleValue()) / m.doubleValue()) * 100;

        return Product.builder()
                .name(name)
                .brand(brand)
                .category(category)
                .seller(seller)
                .price(p)
                .mrp(m)
                .discount(Math.round(disc * 100.0) / 100.0)
                .rating(4.0 + (Math.random() * 1.0))
                .reviewCount(50 + (int)(Math.random() * 5000))
                .isFreeDelivery(Math.random() > 0.5)
                .stock(10 + (int)(Math.random() * 200))
                .shortDescription(shortDesc)
                .description("Professional grade " + name + " from " + brand + ". High quality product with manufacturer warranty.")
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .images(Arrays.asList(imageUrl))
                .estimatedDelivery("3-5 Business Days")
                .countryOfOrigin("India")
                .manufacturer(brand + " Industries")
                .material("Premium Quality")
                .returnAvailable(true)
                .deliveryAvailable(true)
                .warehouse("Central SK Warehouse")
                .build();
    }
}
