package com.example.ecommerce.Model;

import com.example.ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address_line1",nullable = false,length = 500)
    private String addressLine1;

    @Column(name = "address_line2",length = 500)
    private String addressLine2;

    @Column(name = "city",nullable = false,length = 40)
    private String city;

    @Column(name = "country",nullable = false,length = 75)
    private String country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}