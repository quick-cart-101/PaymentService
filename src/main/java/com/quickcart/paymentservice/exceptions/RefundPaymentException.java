package com.quickcart.paymentservice.exceptions;

public class RefundPaymentException extends RuntimeException {
    public RefundPaymentException(String string) {
        super(string);
    }
}
