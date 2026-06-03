package com.campus.food.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.campus.food.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long userId);
}
