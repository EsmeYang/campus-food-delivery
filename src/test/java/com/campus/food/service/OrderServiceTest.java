package com.campus.food.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.campus.food.dto.OrderItemRequest;
import com.campus.food.dto.PlaceOrderRequest;
import com.campus.food.model.Dish;
import com.campus.food.model.Order;
import com.campus.food.repository.DishRepository;
import com.campus.food.repository.OrderItemRepository;
import com.campus.food.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceOrder() {
        // Step 1: create a fake dish (price = 28.00)
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setPrice(new BigDecimal("28.00"));

        // Step 2: tell the mock "when findById(1) is called, return this dish"
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        // Step 3: tell the mock "when orderRepository.save() is called, return the order"
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // Step 4: build the request
        // create a PlaceOrderRequest with userId=1, address="123 University Ave"
        // and one OrderItemRequest with dishId=1, quantity=2
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setUserId(1L);
        request.setAddress("123 University Ave");
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setDishId(1L);
        itemRequest.setQuantity(2);
        request.setOrderItems(List.of(itemRequest));

        // Step 5: call placeOrder
        // call orderService.placeOrder(request)
        Order order = orderService.placeOrder(request);

        // Step 6: verify the result
        // use assertEquals to check userId, totalPrice, status
        assertEquals(1L, order.getUserId());
        assertEquals(new BigDecimal("56.00"), order.getTotalPrice());
        assertEquals(Order.OrderStatus.PENDING_PAYMENT, order.getStatus());
    }
}