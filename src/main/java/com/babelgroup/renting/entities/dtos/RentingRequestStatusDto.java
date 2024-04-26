package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo de estado de solicitud de renting")
public class RentingRequestStatusDto {
    @Schema(description = "Estado de la solicitud de renting", example = "PENDIENTE")
    private String status;
}
