package com.example.loginapp.service;

import com.example.loginapp.model.Order;
import com.example.loginapp.model.PaymentAttempt;
import com.example.loginapp.repository.OrderRepository;
import com.example.loginapp.repository.PaymentAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentAttemptRepository paymentAttemptRepository;

    @Autowired
    private PaymentGatewayClient paymentGatewayClient;

    @Transactional
    public OrderResponse processOrder(Long userId, String requestId, BigDecimal amount, String paymentInfo) throws BusinessException {
        // 1. Check idempotency
        Optional<Order> existing = orderRepository.findByRequestId(requestId);
        if (existing.isPresent()) {
            Order o = existing.get();
            if ("PAID".equals(o.getStatus())) {
                return new OrderResponse(true, "Order already paid", o.getId());
            } else if ("FAILED".equals(o.getStatus())) {
                throw new BusinessException("Previous order attempt failed. Retry manually.");
            }
        }

        // 2. Create Order (status PENDING)
        Order order = new Order();
        order.setUserId(userId);
        order.setAmount(amount);
        order.setStatus("PENDING");
        order.setRequestId(requestId);
        order = orderRepository.save(order);

        // 3. Create PaymentAttempt (status INITIATED)
        PaymentAttempt attempt = new PaymentAttempt();
        attempt.setOrderId(order.getId());
        attempt.setRequestId(requestId);
        attempt.setStatus("INITIATED");
        attempt = paymentAttemptRepository.save(attempt);

        // 4. Call dummy payment gateway
        PaymentResponse payResp;
        try {
            payResp = paymentGatewayClient.charge(paymentInfo, amount);
        } catch (ExternalServiceException e) {
            attempt.setStatus("ERROR");
            attempt.setErrorMessage(e.getMessage());
            paymentAttemptRepository.save(attempt);
            throw new BusinessException("Payment service unavailable. Please try later.");
        }

        // 5. Handle payment response
        if (!payResp.isSuccess()) {
            attempt.setStatus("FAILED");
            attempt.setErrorMessage(payResp.getErrorMessage());
            paymentAttemptRepository.save(attempt);

            order.setStatus("FAILED");
            orderRepository.save(order);

            throw new BusinessException("Payment failed: " + payResp.getErrorMessage());
        }

        // 6. Payment succeeded
        attempt.setStatus("SUCCESS");
        attempt.setPaymentProviderTransactionId(payResp.getTransactionId());
        paymentAttemptRepository.save(attempt);

        order.setStatus("PAID");
        orderRepository.save(order);

        return new OrderResponse(true, "Payment succeeded", order.getId());
    }
}
