package com.tastytown.backend.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastytown.backend.dto.FoodRequestDTO;
import com.tastytown.backend.dto.FoodResponseDTO;
import com.tastytown.backend.service.IFoodService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
@Tag(name = "Food Api", description = "This Controller Manages CRUD operations for Food Entity")
public class FoodController {
    private final IFoodService foodService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ApiResponse(description = "List of all catagories")
    @Operation(summary = "Get all catagories")
    public ResponseEntity<FoodResponseDTO> saveFood(@RequestPart String json,
            @RequestPart MultipartFile foodImage) throws IOException{
        FoodRequestDTO foodRequestDTO = objectMapper.readValue(json, FoodRequestDTO.class);
        return new ResponseEntity<>(foodService.createFood(foodRequestDTO, foodImage), HttpStatus.CREATED);
    } 
}
