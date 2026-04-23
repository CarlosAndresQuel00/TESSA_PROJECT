package com.pack.authapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pack.authapi.models.UserEntity;
import com.pack.authapi.models.OrderEntity;
import com.pack.authapi.repositories.OrderRepository;
import com.pack.authapi.repositories.UserRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getOrders(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe iniciar sesión.");
        }

        String username = authentication.getName();
        return ResponseEntity.ok(orderRepository.findByUserUsername(username));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderEntity order, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe iniciar sesión.");
        }

        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        order.setUser(user);
        orderRepository.save(order);
        return ResponseEntity.ok("Pedido registrado correctamente");
    }
}
