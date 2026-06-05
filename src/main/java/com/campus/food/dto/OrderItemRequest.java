package com.campus.food.dto;

import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {
    @NotNull
    private Integer quantity;
    @NotNull
    private Long dishId;

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getDishId() { return dishId; }
    public void setDishId(Long dishId) { this.dishId = dishId; }
}
