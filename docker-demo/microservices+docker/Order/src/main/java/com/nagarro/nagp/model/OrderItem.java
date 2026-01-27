package com.nagarro.nagp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name="order_items")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemId;
    private String itemName;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne
    @JsonIgnore
    private Order order;

}

