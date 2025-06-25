package com.tastytown.backend.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tastytown.backend.entity.Food;
import com.tastytown.backend.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FoodServiceHelper {
    private final FoodRepository foodRepository;

    @Value("${upload.file.dir}")
    private String FILE_DIR;
    
    public String generateFileName(String fileName) {
        var extensionName = fileName.substring(fileName.lastIndexOf("."));
        var newFileName = UUID.randomUUID().toString();
        return newFileName + extensionName;
    }

    public String uploadFile(MultipartFile foodImage) throws IOException {
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

    public void deleteFoodImage(String foodImageName) throws IOException {
        var file = new File(FILE_DIR + File.separator + foodImageName);
        if (!file.exists()) {
            throw new FileNotFoundException("Food Image not found with name" + foodImageName);
        }
        file.delete();
    }

    public Food getFoodById(String foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new NoSuchElementException("food not found by id" + foodId));
    }
}
