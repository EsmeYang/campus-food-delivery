package com.campus.food.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.campus.food.dto.PlaceOrderRequest;
import com.campus.food.model.Order;
import com.campus.food.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("successfully cancelled order with ID: " + id);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody Order.OrderStatus status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok("successfully updated order with ID: " + id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> viewOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.viewOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }


}
