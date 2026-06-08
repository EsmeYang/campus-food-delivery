package com.campus.food.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.campus.food.exception.ResourceNotFoundException;
import com.campus.food.model.Dish;
import com.campus.food.repository.DishRepository;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private DishService dishService;

    @Test
    public void testGetAvailableDishes() {
        // Setup Redis mock
        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("dishes:available")).thenReturn(null); // cache miss

        // Setup dishes
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Dish 1");
        dish1.setAvailable(true);
        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Dish 2");
        dish2.setAvailable(false);

        // Mock database
        when(dishRepository.findAll()).thenReturn(List.of(dish1, dish2));

        // Call and verify
        List<Dish> result = dishService.getAvailableDish();
        assertEquals(1, result.size());
        assertEquals("Dish 1", result.get(0).getName());
    }

    @Test
    public void testDeleteDishNotFound() {
        // Mock database
        when(dishRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> dishService.deleteDish(1L));
    }
}
