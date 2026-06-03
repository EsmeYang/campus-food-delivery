package com.campus.food.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.campus.food.dto.PlaceOrderRequest;
import com.campus.food.model.Dish;
import com.campus.food.model.Order;
import com.campus.food.repository.DishRepository;
import com.campus.food.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    public Order placeOrder(PlaceOrderRequest request) {
        Order order = new Order();
        Dish dish = dishRepository.findById(request.getDishId())
        .orElseThrow(() -> new RuntimeException("Dish not found"));
        order.setTotalPrice(dish.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setUserId(request.getUserId());
        order.setDishId(request.getDishId());
        order.setQuantity(request.getQuantity());
        order.setAddress(request.getAddress());
        order.setStatus(Order.OrderStatus.PENDING_PAYMENT);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(existingOrder);
    }
    public List<Order> viewOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    public void updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setStatus(status);
        orderRepository.save(existingOrder);
    }
}
