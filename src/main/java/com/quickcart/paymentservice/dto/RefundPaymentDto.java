package com.quickcart.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundPaymentDto {
    private double amount;
    private String paymentId;
}
