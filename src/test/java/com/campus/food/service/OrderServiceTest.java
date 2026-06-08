package com.campus.food.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
import com.campus.food.exception.ResourceNotFoundException;
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

    @Test
    public void testPlaceOrder_DishNotFound() {
        // Step 1: tell the mock "when findById(1) is called, return empty"
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        // Step 2: build the request
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setUserId(1L);
        request.setAddress("123 University Ave");
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setDishId(1L);
        itemRequest.setQuantity(2);
        request.setOrderItems(List.of(itemRequest));

        // Step 3: call placeOrder and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(request));
    }

    @Test
    public void testCancelOrder() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        orderService.cancelOrder(1L);
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void testCancelOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    public void testUpdateOrderStatus() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        orderService.updateOrderStatus(1L, Order.OrderStatus.COMPLETED);
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    public void testPlaceOrderWithMultipleDishes() {
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setPrice(new BigDecimal("10.00"));
        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setPrice(new BigDecimal("20.00"));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dish2));
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setUserId(1L);
        request.setAddress("123 University Ave");
        OrderItemRequest item1 = new OrderItemRequest();
        item1.setDishId(1L);
        item1.setQuantity(2);
        OrderItemRequest item2 = new OrderItemRequest();
        item2.setDishId(2L);
        item2.setQuantity(1);
        request.setOrderItems(List.of(item1, item2));
        Order result = orderService.placeOrder(request);
        assertEquals(new BigDecimal("40.00"), result.getTotalPrice());
    }

}