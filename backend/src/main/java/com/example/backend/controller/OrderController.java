package com.example.loginapp.controller;

import com.example.loginapp.service.BusinessException;
import com.example.loginapp.service.OrderResponse;
import com.example.loginapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint to place order
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest req) {
        try {
            // For simplicity, we use a fixed userId (replace with logged-in user later)
            Long userId = 1L;

            OrderResponse resp = orderService.processOrder(userId, req.getRequestId(), req.getAmount(), req.getPaymentInfo());
            return ResponseEntity.ok(resp);
        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OrderResponse(false, be.getMessage(), null));
        } catch (Exception ex) {
            ex.printStackTrace(); // log for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderResponse(false, "Internal server error. Please try later.", null));
        }
    }
}
