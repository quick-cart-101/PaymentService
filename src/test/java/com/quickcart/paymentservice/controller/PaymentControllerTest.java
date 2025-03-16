package com.quickcart.paymentservice.controller;

import com.quickcart.paymentservice.dto.OrderDto;
import com.quickcart.paymentservice.dto.RefundPaymentDto;
import com.quickcart.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePayment_Success() throws RazorpayException {
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(100.0);

        when(paymentService.createPayment(any(OrderDto.class))).thenReturn("order_id");

        ResponseEntity<String> response = paymentController.createPayment(orderDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("order_id", response.getBody());
    }

    @Test
    public void testCreatePayment_Failure() throws RazorpayException {
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(100.0);

        when(paymentService.createPayment(any(OrderDto.class))).thenThrow(new RazorpayException("Error creating payment"));

        ResponseEntity<String> response = paymentController.createPayment(orderDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error creating payment", response.getBody());
    }

    @Test
    public void testRefundPayment_Success() throws RazorpayException {
        RefundPaymentDto refundPaymentDto = new RefundPaymentDto();
        refundPaymentDto.setAmount(50.0);
        refundPaymentDto.setPaymentId("payment_id");

        when(paymentService.refundPayment(any(RefundPaymentDto.class))).thenReturn("refund_id");

        ResponseEntity<String> response = paymentController.refundPayment(refundPaymentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("refund_id", response.getBody());
    }

    @Test
    public void testRefundPayment_Failure() throws RazorpayException {
        RefundPaymentDto refundPaymentDto = new RefundPaymentDto();
        refundPaymentDto.setAmount(50.0);
        refundPaymentDto.setPaymentId("payment_id");

        when(paymentService.refundPayment(any(RefundPaymentDto.class))).thenThrow(new RazorpayException("Error processing refund"));

        ResponseEntity<String> response = paymentController.refundPayment(refundPaymentDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error processing refund", response.getBody());
    }
}