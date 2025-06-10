package com.tastytown.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String foodId;

    private String foodName;
    private String foodDescription;
    private double foodPrice;
    private String foodImage;

    @ManyToOne
    @JoinColumn(name = "catagory_id")
    private Catagory catagory;
}
