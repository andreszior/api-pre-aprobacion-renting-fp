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

    @Schema(description = "Fecha de solicitud", example = "10-07-1999")
    private Date rentingRequestDate;

    @Schema(description = "Fecha de inicio vigor del renting", example = "10-07-1999")
    private Date effectiveDateRenting;

    @Schema(description = "Fecha de resolución", example = "10-07-1999")
    private Date resolutionDate;

    @Schema(description = "Cantidad de vehículos solicitados", example = "1")
    private Integer numberOfVehicles;

    @Schema(description = "Vehículos solicitados",  example = "[{brand: 'Subaru', model: 'WRX STI', price: 50000.00}, {brand: 'Opel', model: 'Corsa', price: 32130.00}]")
    private List<VehicleDto> vehicles;

    @Schema(description = "Inversión total", example = "50000.00")
    private Double investment;

    @Schema(description = "Cuota", example = "500.00")
    private Double fee;

    @Schema(description = "Plazo (meses)", example = "24")
    private Integer deadline;

    @Schema(description = "Estado de la solicitud", example = "PENDIENTE")
    private String resolution;

    @Schema(description = "Estado de eliminación lógica de la solicitud", example = "0")
    private Integer isDeleted;
}