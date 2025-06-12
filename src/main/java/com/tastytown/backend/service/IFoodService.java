package com.tastytown.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.tastytown.backend.dto.FoodRequestDTO;
import com.tastytown.backend.dto.FoodResponseDTO;

public interface IFoodService {
    FoodResponseDTO createFood(FoodRequestDTO foodRequestDTO, MultipartFile file) throws IOException;
    ResponseEntity<List<FoodResponseDTO>> getAllFoods();

    ResponseEntity<FoodResponseDTO> getFoodById(String foodId);

    // ResponseEntity<FoodResponseDTO> updateFood(String foodId, FoodRequestDTO foodRequestDTO) ;

    void deleteFood(String foodId);
} 
