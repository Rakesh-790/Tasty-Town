package com.tastytown.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    @ApiResponse(description = "all food details extract successfully")
    @Operation(summary = "Get all food details")
    public ResponseEntity<List<FoodResponseDTO>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/{foodId}")
    @ApiResponse(description = "food retrieved successfully by ID")
    @Operation(summary = "Get a food by ID")
    public ResponseEntity<FoodResponseDTO> getFoodById(@PathVariable String foodId) {
        return ResponseEntity.ok(foodService.getFoodById(foodId));

    }

    @PutMapping("/{foodId}")
    @ApiResponse(description = "food update successfully")
    @Operation(summary = "Update a food by Id")
    public ResponseEntity<FoodResponseDTO> updateFood(@PathVariable String foodId, @RequestPart String json,
             @RequestPart(required = false) MultipartFile foodImage) throws IOException{
        FoodRequestDTO dto = objectMapper.readValue(json, FoodRequestDTO.class);
        return ResponseEntity.ok(foodService.updateFood(foodId, dto, foodImage));
    }

    @DeleteMapping("/{foodId}")
    @ApiResponse(description = "deleted successfully")
    @Operation(summary = "Delete a food by Id")
    public ResponseEntity<FoodResponseDTO> deleteFood(@PathVariable String foodId) throws IOException{
        return new ResponseEntity<>(foodService.deleteFood(foodId), HttpStatus.ACCEPTED);
    }

    @PostMapping
    @ApiResponse(description = "extracted the image successfully")
    @Operation(summary = "create details of the food and Image")
    public ResponseEntity<FoodResponseDTO> saveFood(@RequestPart String json,
            @RequestPart MultipartFile foodImage) throws IOException {
        FoodRequestDTO foodRequestDTO = objectMapper.readValue(json, FoodRequestDTO.class);
        FoodResponseDTO foodResponseDTO = foodService.createFood(foodRequestDTO, foodImage);
        return new ResponseEntity<FoodResponseDTO>(foodResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/paginated-foods")
    // @ApiResponse(description = " ")
    @Operation(summary = "Get paginated food details")
    public ResponseEntity<Page<FoodResponseDTO>> getPaginatedFoods(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "8") int pageSize,
            @RequestParam(required = false, defaultValue = "all") String catagoryId,
            @RequestParam(required = false, defaultValue = "all") String search) {
        return ResponseEntity.ok(foodService.getPaginatedFoods(pageNumber, pageSize, catagoryId, search));
    }
}
