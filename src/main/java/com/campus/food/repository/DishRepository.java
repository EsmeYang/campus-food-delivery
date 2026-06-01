package com.campus.food.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.campus.food.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByMerchantId(Long merchantId);
}
