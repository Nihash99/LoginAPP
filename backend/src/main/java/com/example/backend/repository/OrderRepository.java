package com.example.loginapp.repository;

import com.example.loginapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByRequestId(String requestId);
}
