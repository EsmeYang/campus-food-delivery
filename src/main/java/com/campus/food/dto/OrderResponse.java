package com.campus.food.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.campus.food.model.Order;
import com.campus.food.model.OrderItem;

public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private Order.OrderStatus status;
    private String address;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<OrderItem> orderItems;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public Order.OrderStatus getStatus() { return status; }
    public void setStatus(Order.OrderStatus status) { this.status = status; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

}
