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

@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            return;
        }

        // 1. Users
        User admin = userRepository.save(User.builder().name("SK Admin").email("admin@skbazaar.com").password(passwordEncoder.encode("admin123")).role(UserRole.ADMIN).build());
        User seller = userRepository.save(User.builder().name("SK Mega Merchant").email("seller@skbazaar.com").password(passwordEncoder.encode("seller123")).role(UserRole.SELLER).storeName("SK Official Store").walletBalance(new BigDecimal("10000.00")).build());

        // 2. Exact Category Hierarchy from Screenshots
        Category homeCat = categoryRepository.save(Category.builder().name("Home").build());
        
        Category fashion = categoryRepository.save(Category.builder().name("Fashion").build());
        Category men = categoryRepository.save(Category.builder().name("Men").parentCategory(fashion).build());
        Category women = categoryRepository.save(Category.builder().name("Women").parentCategory(fashion).build());
        Category kids = categoryRepository.save(Category.builder().name("Kids").parentCategory(fashion).build());
        Category footwear = categoryRepository.save(Category.builder().name("Footwear").parentCategory(fashion).build());
        Category fashionAcc = categoryRepository.save(Category.builder().name("Fashion Accessories").parentCategory(fashion).build());

        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        Category mobiles = categoryRepository.save(Category.builder().name("Mobiles").parentCategory(electronics).build());
        Category laptops = categoryRepository.save(Category.builder().name("Laptops").parentCategory(electronics).build());
        Category audio = categoryRepository.save(Category.builder().name("Audio").parentCategory(electronics).build());
        Category gaming = categoryRepository.save(Category.builder().name("Gaming").parentCategory(electronics).build());

        Category homeKitchen = categoryRepository.save(Category.builder().name("Home & Kitchen").build());
        Category kitchen = categoryRepository.save(Category.builder().name("Kitchen").parentCategory(homeKitchen).build());
        Category furniture = categoryRepository.save(Category.builder().name("Furniture & Office").build());

        Category beauty = categoryRepository.save(Category.builder().name("Beauty & Personal Care").build());
        Category grocery = categoryRepository.save(Category.builder().name("Grocery & Food").build());
        Category books = categoryRepository.save(Category.builder().name("Books & Education").build());
        Category sports = categoryRepository.save(Category.builder().name("Sports & Fitness").build());
        Category toys = categoryRepository.save(Category.builder().name("Toys & Baby").build());
        Category automotive = categoryRepository.save(Category.builder().name("Automotive").build());
        Category pets = categoryRepository.save(Category.builder().name("Pet Supplies").build());
        Category tools = categoryRepository.save(Category.builder().name("Tools & Hardware").build());
        Category gifts = categoryRepository.save(Category.builder().name("Gifts & Others").build());

        // 3. Populate Products (Minimum 20 Varied Items)
        List<Product> products = new ArrayList<>();

        // ELECTRONICS
        products.add(createProduct("iPhone 15 Pro Max", "Apple", mobiles, seller, "145000", "159900", "Titanium Black, 256GB.", "https://images.unsplash.com/photo-1695048133142-1a20484d2569?q=80&w=600"));
        products.add(createProduct("Samsung Galaxy S24 Ultra", "Samsung", mobiles, seller, "129999", "144000", "Titanium Gray, 512GB.", "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?q=80&w=600"));
        products.add(createProduct("MacBook Air M3", "Apple", laptops, seller, "114900", "124900", "13.6-inch, 8GB RAM, 256GB SSD.", "https://images.unsplash.com/photo-1517336712468-152619371461?q=80&w=600"));
        products.add(createProduct("Sony WH-1000XM5", "Sony", audio, seller, "26990", "34990", "Wireless Noise Cancelling Headphones.", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?q=80&w=600"));
        products.add(createProduct("PlayStation 5 Console", "Sony", gaming, seller, "44990", "54990", "825GB SSD, Digital Edition.", "https://images.unsplash.com/photo-1606813907291-d86ebb9b740e?q=80&w=600"));

        // FASHION
        products.add(createProduct("Men's Regular Fit Shirt", "Louis Philippe", men, seller, "1899", "2999", "Premium cotton formal white shirt.", "https://images.unsplash.com/photo-1598033129183-c4f50c717658?q=80&w=600"));
        products.add(createProduct("Women Silk Saree", "FabIndia", women, seller, "4999", "8500", "Handwoven Banarasi Silk Saree.", "https://images.unsplash.com/photo-1610030469668-93510ec67735?q=80&w=600"));
        products.add(createProduct("Kids Cotton Dungaree", "FirstCry", kids, seller, "899", "1499", "Soft breathable cotton for toddlers.", "https://images.unsplash.com/photo-1519234221713-101791f7ac9e?q=80&w=600"));
        products.add(createProduct("Nike Air Jordan 1", "Nike", footwear, seller, "12995", "16000", "Classic basketball high-top sneakers.", "https://images.unsplash.com/photo-1584735175315-9d5df23860e6?q=80&w=600"));
        products.add(createProduct("Fossil Gen 6 Smartwatch", "Fossil", fashionAcc, seller, "18495", "24995", "Black silicone strap, AMOLED display.", "https://images.unsplash.com/photo-1544006659-f0b21f04cb1d?q=80&w=600"));

        // HOME & KITCHEN
        products.add(createProduct("Air Fryer 4L", "Philips", kitchen, seller, "7499", "9999", "Rapid Air Technology, 90% less fat.", "https://images.unsplash.com/photo-1626078436894-399580665675?q=80&w=600"));
        products.add(createProduct("Non-Stick Cookware Set", "Prestige", kitchen, seller, "2299", "3500", "3-piece induction base set.", "https://images.unsplash.com/photo-1584990344321-27682ad0f144?q=80&w=600"));
        products.add(createProduct("Ergonomic Study Table", "Wakefit", furniture, seller, "3999", "6500", "Engineered wood with storage.", "https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?q=80&w=600"));

        // BEAUTY
        products.add(createProduct("Charcoal Face Wash", "Mamaearth", beauty, seller, "249", "399", "Activated charcoal for skin detox.", "https://images.unsplash.com/photo-1556228578-0d85b1a4d571?q=80&w=600"));
        products.add(createProduct("Matte Finish Foundation", "Maybelline", beauty, seller, "599", "850", "Full coverage long-lasting liquid.", "https://images.unsplash.com/photo-1596462502278-27bfdc4033c8?q=80&w=600"));

        // GROCERY
        products.add(createProduct("Premium Cashews 500g", "Happilo", grocery, seller, "649", "899", "Whole crunchy jumbo cashews.", "https://images.unsplash.com/photo-1509911595633-7228497f13c5?q=80&w=600"));

        // OTHERS
        products.add(createProduct("Atomic Habits Book", "Penguin", books, seller, "450", "799", "Easy & proven way to build habits.", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=600"));
        products.add(createProduct("Yoga Mat with Strap", "Boldfit", sports, seller, "799", "1200", "6mm extra thick TPE material.", "https://images.unsplash.com/photo-1592432678016-e910b452f9a2?q=80&w=600"));
        products.add(createProduct("Remote Control Helicopter", "ToyCloud", toys, seller, "1299", "2500", "3-channel with altitude hold.", "https://images.unsplash.com/photo-1594736797933-d0501ba2fe65?q=80&w=600"));
        products.add(createProduct("Heavy Duty Drill Machine", "Bosch", tools, seller, "3299", "5500", "500W professional impact drill.", "https://images.unsplash.com/photo-1504148455328-c376907d081c?q=80&w=600"));

        productRepository.saveAll(products);
        System.out.println("SK Bazaar: Comprehensive Category Structure & 20+ Detailed Products Live!");
    }

    private Product createProduct(String name, String brand, Category category, User seller, String price, String mrp, String shortDesc, String imageUrl) {
        BigDecimal p = new BigDecimal(price);
        BigDecimal m = new BigDecimal(mrp);
        double disc = ((m.doubleValue() - p.doubleValue()) / m.doubleValue()) * 100;
        return Product.builder()
                .name(name).brand(brand).category(category).seller(seller)
                .price(p).mrp(m).discount(Math.round(disc * 100.0) / 100.0)
                .rating(4.0 + (Math.random() * 1.0)).reviewCount(50 + (int)(Math.random() * 2000))
                .isFreeDelivery(p.intValue() > 499).stock(15 + (int)(Math.random() * 100))
                .shortDescription(shortDesc).description(shortDesc + " High quality " + name + " by " + brand + ". Full manufacturer warranty included.")
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .images(Arrays.asList(imageUrl))
                .estimatedDelivery("3-4 Days").countryOfOrigin("India").manufacturer(brand + " Ltd.").material("Premium Material").returnAvailable(true).deliveryAvailable(true).warehouse("SK Delhi Hub")
                .build();
    }
}
