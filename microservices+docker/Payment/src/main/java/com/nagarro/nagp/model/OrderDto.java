package com.nagarro.nagp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
}
