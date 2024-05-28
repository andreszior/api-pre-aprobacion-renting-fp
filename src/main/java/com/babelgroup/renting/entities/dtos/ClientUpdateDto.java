package com.babelgroup.renting.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Schema(description = "Modelo de datos para actualiar datos de cliente")
public class ClientUpdateDto {

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String name;
    @Schema(description = "Primer Apellido del cliente", example = "Perez")
    private String lastnameFirst;
    @Schema(description = "Segundo Apellido del cliente", example = "Gonzalez")
    private String lastnameSecond;
    @Schema(description = "Pais del cliente ISO3", example = "ES ")
    private String country;
    @Schema(description = "Código de provincia del cliente", example = "28")
    private String provinceCode;
}
