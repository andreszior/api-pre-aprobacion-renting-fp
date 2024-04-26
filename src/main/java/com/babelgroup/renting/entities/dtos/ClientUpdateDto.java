package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Modelo de datos para actualiar datos de cliente")
public class ClientUpdateDto {

    @Schema(description = "Ingresos netos anualesdel cliente", example = "30000.00")
    private double netIncome;
    @Schema(description = "Ingresos brutos anuales del cliente", example = "40000.00")
    private double grossIncome;
    @Schema(description = "Antigüedad laboral del cliente", example = "3")
    private Integer jobAntiquity;
    @Schema(description = "País del cliente", example = "España")
    private String country;
    @Schema(description = "El cif de la empresa del empleado", example = "A12368345")
    private String employerCIF;
    @Schema(description = "El id del empleado", example = "2")
    private Long employeeId;
    @Schema(description = "El id del empleado asalariado", example = "2")
    private Long salariedId;
}
