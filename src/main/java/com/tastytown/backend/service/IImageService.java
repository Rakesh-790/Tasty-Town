package com.tastytown.backend.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface IImageService{
    byte[] extractFoodImages(String foodImageName) throws IOException;
}
