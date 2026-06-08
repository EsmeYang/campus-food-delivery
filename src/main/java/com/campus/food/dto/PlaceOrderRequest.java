package com.campus.food.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaceOrderRequest {

    @NotNull
    private Long userId;
    @NotBlank
    private String address;
    @NotNull
    private List<OrderItemRequest> orderItems;
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<OrderItemRequest> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemRequest> orderItems) { this.orderItems = orderItems; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}