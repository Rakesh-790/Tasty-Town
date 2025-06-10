package com.tastytown.backend.mapper;

import com.tastytown.backend.dto.FoodRequestDTO;
import com.tastytown.backend.dto.FoodResponseDTO;
import com.tastytown.backend.entity.Catagory;
import com.tastytown.backend.entity.Food;

public class FoodMapper {
    private FoodMapper() {

    }

    public static Food convertToEntity(FoodRequestDTO foodRequestDTO, Catagory existingCatagory, String fileName) {
        return Food.builder()
                .foodName(foodRequestDTO.foodName())
                .foodDescription(foodRequestDTO.foodDescription())
                .foodPrice(foodRequestDTO.foodPrice())
                .foodImage(fileName)
                .catagory(existingCatagory)
                .build();
    }

    public static FoodResponseDTO convertToDTO(Food svaedFood) {
        return new FoodResponseDTO(
                svaedFood.getFoodId(),
                svaedFood.getFoodName(),
                svaedFood.getFoodDescription(),
                svaedFood.getFoodPrice(),
                svaedFood.getFoodImage(),
                svaedFood.getCatagory().getCatagoryName(),
                svaedFood.getCatagory().getCatagoryId());
    }
}
