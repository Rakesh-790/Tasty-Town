package com.tastytown.backend.service;

import java.util.List;

import com.tastytown.backend.dto.CatagoryRequestDTO;
import com.tastytown.backend.entity.Catagory;

public interface ICatagoryService {
    /**
     * <h3>Saves a new Category Object.</h3> 
     */
    Catagory saveCatagory(CatagoryRequestDTO requestDTO);
    /**
     * <h3>Retrieves all catagories.</h3>
     */
    List<Catagory> getAllCatagories();
    /**
     * <h3>Retrieves a catagory by its ID.</h3>
     */
    Catagory getCatagoryById(String catagoryId);
    /**
     * <h3>Updates an existing catagory.</h3>
     */
    Catagory updateCatagory(String catagoryId, CatagoryRequestDTO requestDTO);
    /**
     * <h3>Deletes a catagory by its ID.</h3>
     */
    void deleteCatagory(String catagoryId);
}
