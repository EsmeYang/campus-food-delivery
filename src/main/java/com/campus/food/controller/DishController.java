package com.campus.food.controller;

import com.campus.food.model.Dish;
import com.campus.food.service.DishService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) {
        Dish savedDish = dishService.addDish(dish);
        return ResponseEntity.ok(savedDish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok("successfully deleted dish with ID: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        dish.setId(id);
        dishService.updateDish(dish);
        return ResponseEntity.ok("successfully updated dish with ID: " + dish.getId());
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<Dish>> getDishByMerchantId(@PathVariable Long merchantId) {
        List<Dish> dishes = dishService.getDishByMerchantId(merchantId);
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Dish>> getAvailableDish() {
        List<Dish> dishes = dishService.getAvailableDish();
        return ResponseEntity.ok(dishes);
    }

}