package com.campus.food.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import com.campus.food.model.Dish;
import com.campus.food.repository.DishRepository;
@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void updateDish(Dish dish) {
        Dish existingDish = dishRepository.findById(dish.getId())
                .orElseThrow(() -> new RuntimeException("Dish not found"));
        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        existingDish.setDescription(dish.getDescription());
        existingDish.setImageUrl(dish.getImageUrl());
        existingDish.setStock(dish.getStock());
        existingDish.setAvailable(dish.getAvailable());
        dishRepository.save(existingDish);
    }

    public void deleteDish(Long dishId) {
        Dish existingDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
        dishRepository.delete(existingDish);
    }
    public List<Dish> getDishByMerchantId(Long merchantId) {
        return dishRepository.findByMerchantId(merchantId);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Dish> getAvailableDish() {
        if(redisTemplate.opsForValue().get("dishes:available") == null) {
            List<Dish> dishes = dishRepository.findAll().stream()
                .filter(Dish::getAvailable)
                .collect(Collectors.toList());
            redisTemplate.opsForValue().set("dishes:available", dishes, 10, TimeUnit.MINUTES);
            return dishes;
        } else {
            return (List<Dish>) redisTemplate.opsForValue().get("dishes:available");
        }
        
    }
    
}