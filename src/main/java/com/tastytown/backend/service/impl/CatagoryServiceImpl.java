package com.tastytown.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tastytown.backend.dto.CatagoryRequestDTO;
import com.tastytown.backend.entity.Catagory;
import com.tastytown.backend.exception.CatagoryNotFoundException;
import com.tastytown.backend.repository.CatagoryRepository;
import com.tastytown.backend.service.ICatagoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatagoryServiceImpl implements ICatagoryService {
    private final CatagoryRepository catagoryRepository;

    public Catagory saveCatagory(CatagoryRequestDTO requestDTO) {
        var catagory = Catagory.builder()
                .catagoryName(requestDTO.getCatagoryName())
                .build();
        return catagoryRepository.save(catagory);   
    }

    public List<Catagory> getAllCatagories() {
        return catagoryRepository.findAll();
    }

    public Catagory getCatagoryById(String catagoryId) {
        return catagoryRepository.findById(catagoryId)
                .orElseThrow(() -> new CatagoryNotFoundException("Catagory not found with id: " + catagoryId));
    }

    public Catagory updateCatagory(String catagoryId, CatagoryRequestDTO requestDTO) {
        Catagory existingcatagory = getCatagoryById(catagoryId);
        existingcatagory.setCatagoryName(requestDTO.getCatagoryName());
        return catagoryRepository.save(existingcatagory);
    }

    public void deleteCatagory(String catagoryId) {
        Catagory existingcatagory = getCatagoryById(catagoryId);
        catagoryRepository.delete(existingcatagory);
    }
}
