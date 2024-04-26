package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo de garantía")
public class WarrantyDto {
    @Schema(description = "Nif del avalista", example = "G6043364")
    private String guarantorNif;
    @Schema(description = "Valor de la garantía", example = "3124.34")
    private Double guaranteeValue;
    @Schema(description = "Descripción de la garantía", example = "Garantía de 3 años...")
    private String guaranteeDescription;
    @Schema(description = "Tipo de garantía", example = "Personal")
    private String warrantyType;
}
