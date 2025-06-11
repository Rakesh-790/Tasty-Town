package com.tastytown.backend.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastytown.backend.service.IImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@Tag(name = "Image Api", description = "This Controller class responsible for extracting the food Images.")
public class ImageController {
    private final IImageService imageService;

    @GetMapping("/{fileName}")
    @ApiResponse(responseCode = "200" , description = "List of all FoodImage with image name")
    @Operation(summary = "Get a image")
    public ResponseEntity<byte[]> serveImage(@PathVariable String fileName) throws IOException {
        return imageService.extractFoodImages(fileName);
    }
}
