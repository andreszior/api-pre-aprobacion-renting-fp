package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo de vehiculo")
public class VehicleDto {
    @Schema(description = "Marca del vehiculo", example = "Subaru")
    private String brand;
    @Schema(description = "Modelo del vehiculo", example = "WRX STI")
    private String model;
    @Schema(description = "Precio del vehiculo (inversión)", example = "50000.00")
    private Double price;
    @Schema(description = "Cilindrada", example = "2000")
    private Integer cylinderCapacity;
    @Schema(description = "Potencia", example = "300")
    private Integer power;
    @Schema(description = "Número de plazas", example = "5")
    private Integer numberOfSeats;
    @Schema(description = "Cuota base", example = "500.0")
    private Double baseFee;
    @Schema(description = "Color", example = "Rojo")
    private String color;
}
