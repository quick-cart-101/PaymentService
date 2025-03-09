package com.quickcart.paymentservice.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String error) {
        super(error);
    }
}
