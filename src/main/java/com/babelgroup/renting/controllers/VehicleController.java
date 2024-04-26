package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.Extra;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.babelgroup.renting.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
@Tag(
        name = "Vehículo",
        description = "Este controlador tiene la función de tratar los vehículos de renting."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
})
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Obtener vehículos disponibles.",
            description = "Obtienes los vehículos disponibles en para el renting.")
    @ApiResponse(responseCode = "200", description = "Vehículos obtenidos correctamente.",
            content = @Content(schema = @Schema(implementation = VehicleDto.class)))
    @GetMapping("")
    public ResponseEntity<List<VehicleDto>> getVehicles(@RequestParam(value = "brand", required = false) String brand,
                                                        @RequestParam(value = "model", required = false) String model,
                                                        @RequestParam(value = "color", required = false) String color,
                                                        @RequestParam(value = "minBaseFee", required = false) Double minBaseFee,
                                                        @RequestParam(value = "maxBaseFee", required = false) Double maxBaseFee) {
        try {
            List<Vehicle> vehicles = vehicleService.getVehicles(brand, model, color, minBaseFee, maxBaseFee);
            List<VehicleDto> vehicleDtos = vehicleListToDtoList(vehicles);
            return new ResponseEntity<List<VehicleDto>>(vehicleDtos, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<VehicleDto> vehicleListToDtoList(List<Vehicle> vehicles){
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle vehicle: vehicles){
            VehicleDto dto = new VehicleDto();
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
            dto.setPrice(vehicle.getPrice());
            dto.setCylinderCapacity(vehicle.getCylinderCapacity());
            dto.setPower(vehicle.getPower());
            dto.setNumberOfSeats(vehicle.getNumberOfSeats());
            dto.setBaseFee(vehicle.getBaseFee());
            dto.setColor(vehicle.getColor());
            vehicleDtos.add(dto);
        }
        return vehicleDtos;
    }

    @Operation(summary = "Añadir extras al vehículo.",
            description = "Se añadirán extras al vehículo siempre que sea posible.")
    @ApiResponse(responseCode = "200", description = "Extras añadidos correctamente.",
            content = @Content(schema = @Schema(implementation = Extra.class)))
    @PostMapping("/extras")
    public ResponseEntity<String> addExtraToVehicle(@PathVariable long vehicleId, @PathVariable long extraId){
        try {
            return new ResponseEntity<String>(vehicleService.addExtraToVehicle(vehicleId, extraId), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
