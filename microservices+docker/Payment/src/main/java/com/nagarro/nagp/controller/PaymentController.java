package com.nagarro.nagp.controller;

import com.nagarro.nagp.model.Payment;
import com.nagarro.nagp.model.PaymentDto;
import com.nagarro.nagp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.makePayment(paymentDto));
    }

    @GetMapping("/status")
    public ResponseEntity<Payment> getPaymentStatus(@RequestParam String orderId){
        return ResponseEntity.ok(paymentService.getPaymentStatus(Long.valueOf(orderId)));
    }


}
