package com.babelgroup.renting.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class Vehicle {
    private Long id;
    private String brand;
    private String model;
    private Double price;
    private int cylinderCapacity;
    private int power;
    private int numberOfSeats;
    private Double baseFee;
    private String color;
}
