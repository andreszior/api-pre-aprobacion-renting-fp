package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Schema(description = "Modelo de cliente")
public class ClientDto {
    @Schema(description = "DNI del cliente", example = "12345678A")
    private String dni;
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String name;
    @Schema(description = "Scoring del cliente", example = "5")
    private Integer rating;
    @Schema(description = "Primer Apellido del cliente", example = "Perez")
    private String lastnameFirst;
    @Schema(description = "Segundo Apellido del cliente", example = "Gonzalez")
    private String lastnameSecond;
    @Schema(description = "Fecha de nacimiento del cliente", example = "2000-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    @Schema(description = "Pais del cliente ISO3", example = "ES ")
    private String country;
    @Schema(description = "Código de provincia del cliente", example = "28")
    private String provinceCode;
    @Schema(description = "Ingresos netos anuales del cliente", example = "30000.00")
    private Double netIncome;
    @Schema(description = "Ingresos brutos anuales del cliente", example = "40000.00")
    private Double grossIncome;
    @Schema(description = "Antigüedad laboral del cliente", example = "2000-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date jobAntiquity;
    @Schema(description = "Año del salario", example = "2021")
    private Integer salaryYear;
/*    @Schema(description = "ID del empleado", example = "1")
    private Integer employeeId;*/
    @Schema(description = "CIF de la empresa", example = "112345678")
    private String companyCif;
    @Schema(description = "Estado de eliminación lógica del cliente", example = "0")
    private Integer isDeleted;
}
