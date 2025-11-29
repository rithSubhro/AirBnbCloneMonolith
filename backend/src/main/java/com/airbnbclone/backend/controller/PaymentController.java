package com.airbnbclone.backend.controller;

import com.airbnbclone.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestParam String bookingId, @RequestParam int amount) throws Exception {
        return paymentService.createOrder(bookingId, amount);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> body) {
        paymentService.verifyPayment(body);
        return ResponseEntity.ok().build();
    }
}
