package com.tastytown.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tastytown.backend.entity.Catagory;

public interface CatagoryRepository extends JpaRepository<Catagory, String> {
    // Additional query methods can be defined here if needed
    
}
