package com.quickcart.paymentservice.services;

import com.quickcart.paymentservice.dto.OrderDto;
import com.quickcart.paymentservice.dto.RefundPaymentDto;
import com.razorpay.RazorpayException;

public interface PaymentService {
    String createPayment(OrderDto userOrder) throws RazorpayException;

    String refundPayment(RefundPaymentDto refundPaymentDto) throws RazorpayException;
}
