package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.Order;
import com.nagarro.nagp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

