package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Schema(description = "Modelo de solicitud de renting")
public class RentingRequestDto {
    @Schema(description = "Cliente solicitante", example = "30")
    private Long clientId;

    @Schema(description = "Fecha de inicio vigor del renting", example = "10-07-1999")
    private Date effectiveDateRenting;

    @Schema(description = "Vehículos solicitados",  example = "[{brand: 'Subaru', model: 'WRX STI', price: 50000.00}, {brand: 'Opel', model: 'Corsa', price: 32130.00}]")
    private List<VehicleDto> vehicles;
}