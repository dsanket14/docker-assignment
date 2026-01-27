package com.nagarro.nagp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "menu_details")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String restaurantName;
    private String foodName;
    private BigDecimal price;
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
}
