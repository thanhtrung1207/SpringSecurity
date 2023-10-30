package com.example.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name",nullable = false,unique = true)
    private String name;

    @Column(name = "short_description",nullable = false,length = 300)
    private String shortDescription;

    @Column(name = "long_description",nullable = false,length = 300)
    private String longDescription;

    @Column(name = "price",nullable = false)
    private Double price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;

}