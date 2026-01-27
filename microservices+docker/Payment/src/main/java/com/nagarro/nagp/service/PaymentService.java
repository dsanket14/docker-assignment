package com.nagarro.nagp.service;

import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.OrderDto;
import com.nagarro.nagp.model.Payment;
import com.nagarro.nagp.model.PaymentDto;
import com.nagarro.nagp.proxy.ApiProxy;
import com.nagarro.nagp.repo.PaymentRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    PaymentRepo paymentRepo;


    @Autowired
    ApiProxy apiProxy;


    public String makePayment(PaymentDto paymentDto) {
        Payment payment= new Payment();
        OrderDto orderDto= apiProxy.getOrder(paymentDto.getOrderId());
        if(null==orderDto){
            throw new GenericException("400","Order not found");
        }
        payment.setUserId(orderDto.getUserId());
        payment.setOrderId(orderDto.getId());
        payment.setAmount(orderDto.getTotalAmount());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(paymentDto.getStatus());
        paymentRepo.save(payment);
        return "Payment Received";

    }



    public Payment getPaymentStatus(Long OrderId) {
        Payment payment=paymentRepo.findFirstByOrderIdOrderByCreatedAtDesc(OrderId);
        if (null==payment) {
            throw new GenericException("400","Payment Request Not Received Yet");
        }
        return payment;
    }


}
