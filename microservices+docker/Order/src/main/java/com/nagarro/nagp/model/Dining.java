package com.nagarro.nagp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="dining_details")
public class Dining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String timeSlot;
    private Long noOfPeople;
    private String status;

}
