package com.quickcart.paymentservice.services.impl;

import com.quickcart.paymentservice.dto.OrderDto;
import com.quickcart.paymentservice.exceptions.PaymentException;
import com.quickcart.paymentservice.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import static com.quickcart.paymentservice.utils.Constants.AMOUNT;
import static com.quickcart.paymentservice.utils.Constants.CURRENCY;
import static com.quickcart.paymentservice.utils.Constants.ERROR;
import static com.quickcart.paymentservice.utils.Constants.ID;
import static com.quickcart.paymentservice.utils.Constants.INR;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpay;

    public PaymentServiceImpl(RazorpayClient razorpay) {
        this.razorpay = razorpay;
    }

    @Override
    public String createPayment(OrderDto userOrder) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put(AMOUNT,userOrder.getAmount());
        orderRequest.put(CURRENCY, INR);

        Order order = razorpay.orders.create(orderRequest);
        if(order.has(ERROR)) {
            throw new PaymentException(order.get(ERROR).toString());
        }

        return order.get(ID).toString();
    }
}
