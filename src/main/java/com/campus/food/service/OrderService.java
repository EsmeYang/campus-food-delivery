package com.campus.food.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.campus.food.dto.OrderItemRequest;
import com.campus.food.dto.OrderResponse;
import com.campus.food.dto.PlaceOrderRequest;
import com.campus.food.exception.ResourceNotFoundException;
import com.campus.food.model.Dish;
import com.campus.food.model.Order;
import com.campus.food.model.OrderItem;
import com.campus.food.repository.DishRepository;
import com.campus.food.repository.OrderItemRepository;
import com.campus.food.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order placeOrder(PlaceOrderRequest request) {
        // Step 1: create and save the Order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setAddress(request.getAddress());
        order.setStatus(Order.OrderStatus.PENDING_PAYMENT);
        order = orderRepository.save(order);

        // Step 2: loop through items, create OrderItems
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemRequest item : request.getOrderItems()) {
            // fetch dish, create OrderItem, add to totalPrice
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
            BigDecimal itemTotal = dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setDishId(dish.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
        }

        // Step 3: update total price and save Order again
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        existingOrder.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(existingOrder);
    }
    public List<OrderResponse> viewOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> responses = new ArrayList<>();
        // for each order, build an OrderResponse
        // return the list of OrderResponse
        for(Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserId(order.getUserId());
            response.setTotalPrice(order.getTotalPrice());
            response.setStatus(order.getStatus());
            response.setAddress(order.getAddress());
            response.setCreatedAt(order.getCreatedAt());
            response.setOrderItems(orderItems);
            responses.add(response);
        }
        return responses;
    }
    public void updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        existingOrder.setStatus(status);
        orderRepository.save(existingOrder);
    }
}
