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

        // 2. Create Categories Hierarchy as per Images
        // 1. Fashion
        Category fashion = categoryRepository.save(Category.builder().name("Fashion").build());
        Category men = categoryRepository.save(Category.builder().name("Men").parentCategory(fashion).build());
        String[] menSub = {"T-Shirts", "Shirts", "Jeans", "Trousers", "Jackets", "Kurta & Ethnic Wear", "Innerwear", "Accessories"};
        for (String s : menSub) categoryRepository.save(Category.builder().name(s).parentCategory(men).build());
        
        Category women = categoryRepository.save(Category.builder().name("Women").parentCategory(fashion).build());
        String[] womenSub = {"Sarees", "Kurtis", "Suits", "Dresses", "Tops", "Jeans", "Leggings", "Handbags"};
        for (String s : womenSub) categoryRepository.save(Category.builder().name(s).parentCategory(women).build());
        
        Category kids = categoryRepository.save(Category.builder().name("Kids").parentCategory(fashion).build());
        String[] kidsSub = {"Boys Clothing", "Girls Clothing", "Baby Clothing", "Kids Footwear"};
        for (String s : kidsSub) categoryRepository.save(Category.builder().name(s).parentCategory(kids).build());

        // 2. Electronics
        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        String[] elecSub = {"Mobiles", "Tablets", "Laptops", "Computers", "Headphones & Earphones", "Smart Watches", "Speakers", "Cameras", "Printers", "Mobile Accessories"};
        for (String s : elecSub) categoryRepository.save(Category.builder().name(s).parentCategory(electronics).build());

        // 3. Home & Kitchen
        Category homeKitchen = categoryRepository.save(Category.builder().name("Home & Kitchen").build());
        Category kitchen = categoryRepository.save(Category.builder().name("Kitchen").parentCategory(homeKitchen).build());
        String[] kitchSub = {"Cookware", "Dinner Sets", "Storage", "Kitchen Tools"};
        for (String s : kitchSub) categoryRepository.save(Category.builder().name(s).parentCategory(kitchen).build());
        String[] hkSub = {"Home Decor", "Furniture", "Bedsheets", "Curtains", "Lighting", "Cleaning Supplies"};
        for (String s : hkSub) categoryRepository.save(Category.builder().name(s).parentCategory(homeKitchen).build());

        // 4. Beauty & Personal Care
        Category beauty = categoryRepository.save(Category.builder().name("Beauty & Personal Care").build());
        String[] beautySub = {"Makeup", "Skincare", "Haircare", "Perfumes", "Men's Grooming", "Bath & Body"};
        for (String s : beautySub) categoryRepository.save(Category.builder().name(s).parentCategory(beauty).build());

        // 5. Grocery
        Category grocery = categoryRepository.save(Category.builder().name("Grocery").build());
        String[] grocSub = {"Atta & Flour", "Rice", "Dal & Pulses", "Edible Oil", "Spices", "Dry Fruits", "Snacks", "Beverages", "Tea & Coffee"};
        for (String s : grocSub) categoryRepository.save(Category.builder().name(s).parentCategory(grocery).build());

        // 6. Sports & Fitness
        Category sports = categoryRepository.save(Category.builder().name("Sports & Fitness").build());
        String[] sportsSub = {"Gym Equipment", "Yoga", "Cricket", "Football", "Badminton", "Sports Shoes", "Fitness Accessories"};
        for (String s : sportsSub) categoryRepository.save(Category.builder().name(s).parentCategory(sports).build());

        // 7. Books & Stationery
        Category books = categoryRepository.save(Category.builder().name("Books & Stationery").build());
        String[] booksSub = {"Books", "School Supplies", "Office Supplies", "Notebooks", "Pens", "Art & Craft"};
        for (String s : booksSub) categoryRepository.save(Category.builder().name(s).parentCategory(books).build());

        // 8. Toys & Baby
        Category toys = categoryRepository.save(Category.builder().name("Toys & Baby").build());
        String[] toysSub = {"Toys", "Baby Care", "Diapers", "Feeding", "Baby Toys", "Baby Accessories"};
        for (String s : toysSub) categoryRepository.save(Category.builder().name(s).parentCategory(toys).build());

        // 9. Automotive
        Category automotive = categoryRepository.save(Category.builder().name("Automotive").build());
        String[] autoSub = {"Car Accessories", "Bike Accessories", "Helmets", "Car Care", "Bike Care", "Tools"};
        for (String s : autoSub) categoryRepository.save(Category.builder().name(s).parentCategory(automotive).build());

        // 10. Health & Wellness
        Category health = categoryRepository.save(Category.builder().name("Health & Wellness").build());
        String[] healthSub = {"Vitamins & Supplements", "Fitness Nutrition", "Personal Care", "Wellness Products", "Healthcare Devices"};
        for (String s : healthSub) categoryRepository.save(Category.builder().name(s).parentCategory(health).build());

        // 11. Shoes & Footwear
        Category shoes = categoryRepository.save(Category.builder().name("Shoes & Footwear").build());
        String[] shoesSub = {"Men's Shoes", "Women's Shoes", "Kids Shoes", "Sports Shoes", "Sandals", "Slippers"};
        for (String s : shoesSub) categoryRepository.save(Category.builder().name(s).parentCategory(shoes).build());

        // 12. Jewellery & Accessories
        Category jewellery = categoryRepository.save(Category.builder().name("Jewellery & Accessories").build());
        String[] jewSub = {"Gold Jewellery", "Silver Jewellery", "Fashion Jewellery", "Watches", "Sunglasses", "Bags & Wallets"};
        for (String s : jewSub) categoryRepository.save(Category.builder().name(s).parentCategory(jewellery).build());

        // 13. Pet Supplies
        Category pets = categoryRepository.save(Category.builder().name("Pet Supplies").build());
        String[] petsSub = {"Dog", "Cat", "Pet Food", "Pet Toys", "Pet Grooming"};
        for (String s : petsSub) categoryRepository.save(Category.builder().name(s).parentCategory(pets).build());

        // 14. Tools & Hardware
        Category tools = categoryRepository.save(Category.builder().name("Tools & Hardware").build());
        String[] toolsSub = {"Hand Tools", "Power Tools", "Electrical", "Plumbing", "Hardware", "Safety Equipment"};
        for (String s : toolsSub) categoryRepository.save(Category.builder().name(s).parentCategory(tools).build());


        // 3. Create Sample Products for different sections as per Front Dashboard requirement (APPROVED status)
        
        // Product 1: Samsung Earbuds (Flash Sale)
        Product p1 = productRepository.save(Product.builder()
                .name("Samsung Earbuds")
                .brand("Samsung")
                .price(new BigDecimal("1299"))
                .mrp(new BigDecimal("2499"))
                .discount(48.0)
                .rating(4.4)
                .reviewCount(1520)
                .isFreeDelivery(true)
                .stock(50)
                .shortDescription("High fidelity wireless noise cancelling earbuds.")
                .description("High fidelity wireless noise cancelling earbuds with fast pairing.")
                .category(electronics)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .offerPrice(new BigDecimal("1199"))
                .lowStockAlert(5)
                .warehouse("Mumbai Central WH")
                .material("Premium Polycarbonate")
                .countryOfOrigin("India")
                .manufacturer("Samsung Electronics India Pvt Ltd")
                .deliveryAvailable(true)
                .deliveryCharge(BigDecimal.ZERO)
                .estimatedDelivery("2-3 Days")
                .returnAvailable(true)
                .build());

        // Product 2: Nike Cotton T-Shirt (Fashion Deals)
        Product p2 = productRepository.save(Product.builder()
                .name("Nike Cotton T-Shirt")
                .brand("Nike")
                .price(new BigDecimal("799"))
                .mrp(new BigDecimal("1499"))
                .discount(46.0)
                .rating(4.5)
                .reviewCount(840)
                .isFreeDelivery(true)
                .stock(100)
                .shortDescription("100% pure organic breathable cotton material.")
                .description("100% pure organic breathable cotton material suitable for summer wear.")
                .category(fashion)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .offerPrice(new BigDecimal("749"))
                .lowStockAlert(10)
                .warehouse("Delhi NCR WH")
                .material("Organic Cotton")
                .countryOfOrigin("India")
                .manufacturer("Nike India Retail Pvt Ltd")
                .deliveryAvailable(true)
                .deliveryCharge(BigDecimal.ZERO)
                .estimatedDelivery("3-5 Days")
                .returnAvailable(true)
                .build());

        // Product 3: Cookware Set (Home & Kitchen)
        productRepository.save(Product.builder()
                .name("Non-Stick Cookware Set")
                .brand("Prestige")
                .price(new BigDecimal("2499"))
                .mrp(new BigDecimal("3999"))
                .discount(37.0)
                .rating(4.3)
                .reviewCount(530)
                .isFreeDelivery(true)
                .stock(60)
                .category(homeKitchen)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .build());

        // Product 4: Deals under 499 (Mug)
        productRepository.save(Product.builder()
                .name("Handmade Coffee Mug")
                .brand("HomeDeco")
                .price(new BigDecimal("299"))
                .mrp(new BigDecimal("599"))
                .discount(50.0)
                .rating(4.2)
                .reviewCount(110)
                .isFreeDelivery(false)
                .stock(500)
                .category(homeKitchen)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .build());

        // Product 5: Charcoal Face Wash (Beauty)
        productRepository.save(Product.builder()
                .name("Charcoal Face Wash")
                .brand("Garnier")
                .price(new BigDecimal("199"))
                .mrp(new BigDecimal("299"))
                .discount(33.0)
                .rating(4.1)
                .reviewCount(1200)
                .isFreeDelivery(true)
                .stock(300)
                .category(beauty)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .build());

        // Product 6: Organic Basmati Rice (Grocery)
        productRepository.save(Product.builder()
                .name("Organic Basmati Rice 5kg")
                .brand("India Gate")
                .price(new BigDecimal("499"))
                .mrp(new BigDecimal("749"))
                .discount(33.0)
                .rating(4.6)
                .reviewCount(2400)
                .isFreeDelivery(true)
                .stock(150)
                .category(grocery)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .build());

        // Product 7: Premium Yoga Mat (Sports & Fitness)
        productRepository.save(Product.builder()
                .name("Premium Yoga Mat")
                .brand("Boldfit")
                .price(new BigDecimal("399"))
                .mrp(new BigDecimal("999"))
                .discount(60.0)
                .rating(4.4)
                .reviewCount(950)
                .isFreeDelivery(false)
                .stock(400)
                .category(sports)
                .seller(seller)
                .status(com.example.skbazaar.model.enums.ProductStatus.APPROVED)
                .build());

        // 4. Add Product Variants as per Example
        variantRepository.save(ProductVariant.builder()
                .product(p2).color("Black").size("M").stock(30).price(new BigDecimal("799")).build());
        variantRepository.save(ProductVariant.builder()
                .product(p2).color("Black").size("L").stock(40).price(new BigDecimal("799")).build());
        variantRepository.save(ProductVariant.builder()
                .product(p2).color("Blue").size("M").stock(30).price(new BigDecimal("799")).build());

        System.out.println("SK Bazaar highly structured Category Hierarchy and Products seeded successfully!");
    }
}
