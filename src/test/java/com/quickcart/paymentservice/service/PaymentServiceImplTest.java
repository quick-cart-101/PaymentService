package com.quickcart.paymentservice.service;

import com.quickcart.paymentservice.dto.OrderDto;
import com.quickcart.paymentservice.dto.RefundPaymentDto;
import com.quickcart.paymentservice.exceptions.RefundPaymentException;
import com.quickcart.paymentservice.services.impl.PaymentServiceImpl;
import com.razorpay.Order;
import com.razorpay.OrderClient;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;
import com.razorpay.PaymentClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private RazorpayClient razorpayClient;

    @Mock
    private Order order;

    @Mock
    private Refund refund;

    @Mock
    private OrderClient orderClient;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private OrderDto orderDto;
    private RefundPaymentDto refundPaymentDto;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setAmount(1000);

        refundPaymentDto = new RefundPaymentDto();
        refundPaymentDto.setAmount(500);
        refundPaymentDto.setPaymentId("pay_ABC123");

        // Inject mock orderClient and paymentClient into the razorpayClient using reflection
        ReflectionTestUtils.setField(razorpayClient, "orders", orderClient);
        ReflectionTestUtils.setField(razorpayClient, "payments", paymentClient);
    }

    @Test
    void createPayment_ShouldReturnOrderId_WhenSuccessful() throws RazorpayException {
        when(orderClient.create(any(JSONObject.class))).thenReturn(order);
        when(order.get("id")).thenReturn("order123");

        String result = paymentService.createPayment(orderDto);

        assertEquals("order123", result);

        // Verify interactions
        verify(orderClient).create(any(JSONObject.class));
        verify(order).get("id");
    }

    @Test
    void refundPayment_ShouldReturnRefundId_WhenSuccessful() throws RazorpayException {
        when(paymentClient.refund(eq("pay_ABC123"), any(JSONObject.class))).thenReturn(refund);
        when(refund.get("id")).thenReturn("refund_123");

        String result = paymentService.refundPayment(refundPaymentDto);

        assertEquals("refund_123", result);

        // Verify interactions
        verify(paymentClient).refund(eq("pay_ABC123"), any(JSONObject.class));
        verify(refund).get("id");
    }

    @Test
    void refundPayment_ShouldThrowRefundPaymentException_WhenErrorOccurs() throws RazorpayException {
        when(paymentClient.refund(eq("pay_ABC123"), any(JSONObject.class))).thenReturn(refund);
        when(refund.has("error")).thenReturn(true);
        when(refund.get("error")).thenReturn("Refund failed");

        RefundPaymentException exception = assertThrows(RefundPaymentException.class, () ->
                paymentService.refundPayment(refundPaymentDto)
        );

        assertEquals("Refund failed", exception.getMessage());

        // Verify interactions
        verify(paymentClient).refund(eq("pay_ABC123"), any(JSONObject.class));
        verify(refund).has("error");
        verify(refund).get("error");
    }
}
