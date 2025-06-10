package com.tastytown.backend.dto;

public record FoodResponseDTO(String foodId, String foodName, String description, double price, String foodImage,
        String catagoryName, String catagoryId) {
            
}
