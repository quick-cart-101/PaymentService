package com.quickcart.paymentservice.services;

import com.quickcart.paymentservice.dto.OrderDto;
import com.razorpay.RazorpayException;

public interface PaymentService {
    String createPayment(OrderDto userOrder) throws RazorpayException;
}
