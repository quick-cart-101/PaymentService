package com.quickcart.paymentservice.controller;

import com.quickcart.paymentservice.dto.OrderDto;
import com.quickcart.paymentservice.dto.RefundPaymentDto;
import com.quickcart.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody OrderDto userOrder) {
        try {
            return new ResponseEntity<>(paymentService.createPayment(userOrder), HttpStatus.CREATED);
        } catch (RazorpayException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refundPayment(@RequestBody RefundPaymentDto refundPaymentDto) {
        try {
            return new ResponseEntity<>(paymentService.refundPayment(refundPaymentDto), HttpStatus.OK);
        } catch (RazorpayException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
