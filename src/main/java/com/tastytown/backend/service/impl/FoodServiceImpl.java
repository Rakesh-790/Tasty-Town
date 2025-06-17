package com.tastytown.backend.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tastytown.backend.dto.FoodRequestDTO;
import com.tastytown.backend.dto.FoodResponseDTO;
import com.tastytown.backend.entity.Food;
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
        // return food.stream().map(foodItem ->
        // FoodMapper.convertToDTO(foodItem)).toList();
        return food.stream().map(FoodMapper::convertToDTO).toList();
    }

    @Override
    public FoodResponseDTO getFoodById(String foodId) {
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new NoSuchElementException("Food not found with id: " + foodId));
        return FoodMapper.convertToDTO(food);
    }

    @Override
    public FoodResponseDTO updateFood(String foodId,
            FoodRequestDTO dto, MultipartFile foodImage) throws IOException {
        var existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new NoSuchElementException("Food not found with id: " + foodId));
        // var catagory = catagoryService.getCatagoryById(dto.catagroyId());
        existingFood.setFoodName(dto.foodName());
        existingFood.setFoodPrice(dto.foodPrice());
        existingFood.setFoodDescription(dto.foodDescription());

        if (foodImage != null && !foodImage.isEmpty()) {
            deleteFoodImage(existingFood.getFoodImage());
            var newFoodImageName = uploadFile(foodImage);
            existingFood.setFoodImage(newFoodImageName);
            if (dto.catagroyId() != null && !dto.catagroyId().isEmpty()) {
                var catagories = catagoryService.getCatagoryById(dto.catagroyId());
                existingFood.setCatagory(catagories);
            }
        }
        var savedFood = foodRepository.save(existingFood);
        return FoodMapper.convertToDTO(savedFood);
    }

    @Override
    public FoodResponseDTO deleteFood(String foodId) throws IOException {
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new NoSuchElementException("Food not found with id: " + foodId));
        deleteFoodImage(food.getFoodImage());
        foodRepository.deleteById(foodId);
        return FoodMapper.convertToDTO(food);

    }

    private void deleteFoodImage(String foodImageName) throws IOException {
        var file = new File(FILE_DIR + File.separator + foodImageName);
        if (!file.exists()) {
            throw new FileNotFoundException("Food Image not found with name" + foodImageName);
        }
        file.delete();
    }

    @Override
    public Page<FoodResponseDTO> getPaginatedFoods(int pageNumber, int pageSize, String catagoryId, String search) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // filternation
        Page<Food> foodPage;
        if (catagoryId != null && !catagoryId.equals("all") && !search.equals("all")) {
            foodPage = foodRepository.findByCatagory_CatagoryIdAndFoodNameContainingIgnoreCase(catagoryId, search,
                    pageable);
        } else if (!catagoryId.equals("all")) {
            foodPage = foodRepository.findByCatagory_CatagoryId(catagoryId, pageable);
        } else if (!search.equals("all")) {
            foodPage = foodRepository.findByFoodNameContainingIgnoreCase(search, pageable);
        } else {
            foodPage = foodRepository.findAll(pageable);
        }
        return foodPage.map(FoodMapper::convertToDTO);
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
