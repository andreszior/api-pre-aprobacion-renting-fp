package com.babelgroup.renting.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Schema(description = "Modelo de renta del cliente")
public class IncomeDTO {
    @Schema(description = "ID del cliente", example = "1")
    private Long clientId;
    @Schema(description = "Ingresos netos anuales del cliente", example = "30000.00")
    private Double netIncome;
    @Schema(description = "Ingresos brutos anuales del cliente", example = "40000.00")
    private Double grossIncome;
    @Schema(description = "Antigüedad laboral del cliente", example = "2000-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date jobAntiquity;
    @Schema(description = "Cuenta propia", example = "true")
    @JsonProperty
    private boolean isFreelance;
    @Schema(description = "Año del salario", example = "2021")
    private Integer salaryYear;
    @Schema(description = "CIF de la empresa", example = "112345678")
    private String companyCif;
}
