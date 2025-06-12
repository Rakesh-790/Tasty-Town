package com.tastytown.backend.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tastytown.backend.dto.FoodRequestDTO;
import com.tastytown.backend.dto.FoodResponseDTO;
import com.tastytown.backend.exception.CatagoryNotFoundException;
// import com.tastytown.backend.entity.Food;
import com.tastytown.backend.mapper.FoodMapper;
// import com.tastytown.backend.repository.CatagoryRepository;
import com.tastytown.backend.repository.FoodRepository;
import com.tastytown.backend.service.ICatagoryService;
import com.tastytown.backend.service.IFoodService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements IFoodService {
    // private final CatagoryRepository catagoryRepository;
    private final ICatagoryService catagoryService;
    private final FoodRepository foodRepository;

    @Value("${upload.file.dir}")
    private String FILE_DIR;

    @Override
    public FoodResponseDTO createFood(FoodRequestDTO foodRequestDTO, MultipartFile foodImage) throws IOException {
        var existingCatagory = catagoryService.getCatagoryById(foodRequestDTO.catagroyId());
        // save the image in the folder
        var fileName = uploadFile(foodImage);

        // save the food in the database
        var food = FoodMapper.convertToEntity(foodRequestDTO, existingCatagory, fileName);
        var savedFood = foodRepository.save(food);

        return FoodMapper.convertToDTO(savedFood);
    }

    private String generateFileName(String fileName) {
        var extensionName = fileName.substring(fileName.lastIndexOf("."));
        var newFileName = UUID.randomUUID().toString();
        return newFileName + extensionName;
    }

    @Override
    public List<FoodResponseDTO> getAllFoods() {
        var food = foodRepository.findAll();
        // return food.stream().map(foodItem -> FoodMapper.convertToDTO(foodItem)).toList();
        return food.stream().map(FoodMapper :: convertToDTO).toList();
    }

    @Override
    public FoodResponseDTO getFoodById(String foodId) {
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new NoSuchElementException("Food not found with id: " + foodId));
        return FoodMapper.convertToDTO(food);
    }

    // @Override
    // public ResponseEntity<FoodResponseDTO> updateFood(String foodId, FoodRequestDTO foodRequestDTO) {
    //     var existingFood = getFoodById(foodId);
    //     var updatedFood = existingFood.getBody();
            
    //     }
    // }

    @Override
    public void deleteFood(String foodId) {
        getFoodById(foodId);
        foodRepository.deleteById(foodId);
    }

    private String uploadFile(MultipartFile foodImage) throws IOException {
        if (!foodImage.isEmpty()) {
            var fileName = foodImage.getOriginalFilename();// it extracts the file name (including extension)
            var newFileName = generateFileName(fileName);

            var fos = new FileOutputStream(FILE_DIR + File.separator + newFileName);
            fos.write(foodImage.getBytes());
            fos.close();
            return newFileName;
        }
        throw new FileNotFoundException("File is empty. Food Image is not uploaded");
    }
}
