package com.tastytown.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tastytown.backend.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String> {
    Page<Food> findByCatagory_CatagoryIdAndFoodNameContainingIgnoreCase(String catagoryId, String foodName,
            Pageable pageable);

    Page<Food> findByFoodNameContainingIgnoreCase(String search, Pageable pageable);

    Page<Food> findByCatagory_CatagoryId(String catagoryId, Pageable pageable);
    
}
