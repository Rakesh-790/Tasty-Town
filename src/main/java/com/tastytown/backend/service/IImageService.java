package com.tastytown.backend.service;

import java.io.IOException;

public interface IImageService {
    byte[] extractFoodImages(String foodImageName) throws IOException;
}
