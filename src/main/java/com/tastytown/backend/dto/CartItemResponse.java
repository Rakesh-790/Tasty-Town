package com.tastytown.backend.dto;

public record CartItemResponse(String foodId, int quantity, String foodName, String foodCatagoryName,
        double foodPrice) {

}
