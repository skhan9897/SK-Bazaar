package com.example.skbazaar.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String brand;
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> images;

    @ElementCollection
    private List<String> videos;

    private BigDecimal price;
    private BigDecimal mrp;
    private Double discount;
    private Double rating;
    private Integer reviewCount;
    private Boolean isFreeDelivery;
    private Double gst;
    private Integer stock;
    private Double weight;
    private String dimensions;
    private String size;
    private String color;

    // New Fields as per the Product Details Structure Diagram
    private String shortDescription;
    private BigDecimal offerPrice;
    private Integer lowStockAlert;
    private String warehouse;
    private String material;
    private String countryOfOrigin;
    private String manufacturer;
    private Boolean deliveryAvailable;
    private BigDecimal deliveryCharge;
    private String estimatedDelivery;
    private Boolean returnAvailable;

    @Column(columnDefinition = "TEXT")
    private String returnPolicy;

    @Column(columnDefinition = "TEXT")
    private String warranty;

    @Column(columnDefinition = "TEXT")
    private String deliveryInformation;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Enumerated(EnumType.STRING)
    private com.example.skbazaar.model.enums.ProductStatus status = com.example.skbazaar.model.enums.ProductStatus.PENDING;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;
}
