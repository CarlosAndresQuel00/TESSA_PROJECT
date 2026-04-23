package com.pack.authapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pack.authapi.models.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserUsername(String username);
}
