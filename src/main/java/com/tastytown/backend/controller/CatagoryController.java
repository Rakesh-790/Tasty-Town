package com.tastytown.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tastytown.backend.dto.CatagoryRequestDTO;
import com.tastytown.backend.entity.Catagory;
import com.tastytown.backend.service.ICatagoryService;
// import com.tastytown.backend.service.CatagoryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catagories")
@Tag(name = "Catagory Api", description = "This Controller Manages CRUD operations for Catagory Entity")
public class CatagoryController {
    private final ICatagoryService catagoryService;

    @PostMapping
    @Operation(summary = "Create a new catagory")
    public ResponseEntity<Catagory> createCatagory(@RequestBody CatagoryRequestDTO requestDTO) {
        return new ResponseEntity<>(catagoryService.saveCatagory(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiResponse(description = "List of all catagories")
    @Operation(summary = "Get all catagories")
    public ResponseEntity<List<Catagory>> getAllCatagories() {
        return ResponseEntity.ok(catagoryService.getAllCatagories());
    }

    @GetMapping("/{catagoryId}")
    @ApiResponse(description = "Catagory retrieved successfully by ID")
    @Operation(summary = "Get a catagory by ID")
    public ResponseEntity<Catagory> getCatagoryById(@PathVariable String catagoryId) {
        return ResponseEntity.ok(catagoryService.getCatagoryById(catagoryId));
    }

    @PutMapping("/{catagoryId}")
    @ApiResponse(description = "Catagory updated successfully")
    @Operation(summary = "Update a catagory by ID")
    public ResponseEntity<Catagory> updateCatagory(@PathVariable String catagoryId,
            @RequestBody CatagoryRequestDTO requestDTO) {
        return ResponseEntity.ok(catagoryService.updateCatagory(catagoryId, requestDTO));
    }

    @DeleteMapping("/{catagoryId}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(description = "Catagory deleted successfully")
    @Operation(summary = "Delete a catagory by ID")
    public ResponseEntity<Void> deleteCatagory(@PathVariable String catagoryId) {
        catagoryService.deleteCatagory(catagoryId);
        return ResponseEntity.noContent().build();
    }
}
