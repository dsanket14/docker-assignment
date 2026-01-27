package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
       Payment findFirstByOrderIdOrderByCreatedAtDesc(Long orderId);
}
