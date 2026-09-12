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
    private Double gst;
    private Integer stock;
    private Double weight;
    private String dimensions;
    private String size;
    private String color;

    @Column(columnDefinition = "TEXT")
    private String returnPolicy;

    @Column(columnDefinition = "TEXT")
    private String warranty;

    @Column(columnDefinition = "TEXT")
    private String deliveryInformation;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
