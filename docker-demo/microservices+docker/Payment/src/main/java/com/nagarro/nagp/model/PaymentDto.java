package com.nagarro.nagp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class PaymentDto {
    private Long orderId;
    private String status;
}
