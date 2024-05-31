package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.entities.dtos.RentingRequestStatusDto;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.validators.RentingRequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentingRequest")
@Tag(
        name = "Solicitudes de renting.",
        description = "Este controlador tiene la función de tratar las solicitudes de renting."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
})
public class RentingRequestController {
    private final RentingRequestService rentingRequestService;
    private final RentingRequestValidator rentingRequestValidator;

    public RentingRequestController(RentingRequestService rentingRequestService, RentingRequestValidator rentingRequestValidator){
        this.rentingRequestService = rentingRequestService;
        this.rentingRequestValidator = rentingRequestValidator;
    }

    @PostMapping("")
    @Operation(summary = "Crear petición", description = "Dada la información de entrada, crea una petición.")
    @ApiResponse(responseCode = "201", description = "Petición creada correctamente", content = @Content(schema = @Schema(implementation = RentingRequestDto.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos de la peticion requeridos para el registro.", required = true,
            content = @Content(schema = @Schema(implementation = RentingRequestDto.class)))
    public ResponseEntity<?> createRentingRequest(@Valid @RequestBody RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {

        BindingResult bindingResult = new DataBinder(rentingRequestDto).getBindingResult();
        rentingRequestValidator.validate(rentingRequestDto, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        RentingRequest newRentingRequest = rentingRequestService.createRentingRequest(rentingRequestDto);

        try {
            return new ResponseEntity<>(newRentingRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{rentingRequestId}")
    @Operation(summary = "Obtener la Renting request correspondiente al Id indicado.",
            description = "Dado un id por parámetro, obtiene la Renting Request con Id rentingRequestId.")
    @ApiResponse(responseCode = "200", description = "Renting request obtenida correctamente.",
            content = @Content(schema = @Schema(implementation = RentingRequestDto.class)))
    @ApiResponse(responseCode = "404", description = "Renting request no encontrada.")
    public ResponseEntity<?> getRentingRequest(@PathVariable long rentingRequestId) {
        try {
            RentingRequest rentingRequest = rentingRequestService.getRentingRequest(rentingRequestId);
            System.out.println(rentingRequest);

            return new ResponseEntity<>(rentingRequest, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{rentingRequestId}")
    @Operation(summary = "Eliminar la Renting request correspondiente al Id indicado.",
            description = "Dado un id por parámetro, elimina la Renting Request con Id rentingRequestId.")
    @ApiResponse(responseCode = "200", description = "Renting request eliminada correctamente.")
    @ApiResponse(responseCode = "404", description = "Renting request no encontrada.")
    public ResponseEntity<Void> deleteRentingRequest(@PathVariable long rentingRequestId) throws RentingRequestNotFoundException {
        boolean deleted = rentingRequestService.deleteRentingRequest(rentingRequestId);

        if (deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{rentingRequestStatus}")
    @Operation(summary = "Se obtiene una lista de solicitudes con el Status indicado.",
            description = "Dado un rentingRequestStatus por parámetro, se obtiene una lista de solicitudes con Status rentingRequestStatus.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes filtradas correctamente.")
    public ResponseEntity<List<RentingRequest>> getFilteredRentingRequests(@PathVariable String rentingRequestStatus) {
        if (rentingRequestStatus == null || rentingRequestStatus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            String description = RequestResult.getDescriptionByName(rentingRequestStatus);
            List<RentingRequest> rentingRequests = rentingRequestService.getFilteredRentingRequests(description);
            return new ResponseEntity<>(rentingRequests, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{rentingRequestId}/status")
    @Operation(summary = "Actualizar el estado de la solicitud de renting con Id rentingRequestId.",
            description = "Dado un id y un DTO indicando el tipo de solicitud, actualiza manualmente el estado de dicha solicitud de renting.")
    @ApiResponse(responseCode = "200", description = "Estado de la solicitud actualizado correctamente.",
            content =@Content(schema = @Schema(implementation = RentingRequestDto.class)))
    @ApiResponse(responseCode = "404", description = "Solicitud de renting no encontrada.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO del estado de la solicitud que contiene el nuevo estado.",
            required = true,
            content = @Content(schema = @Schema(implementation = RentingRequestStatusDto.class)))
    public ResponseEntity<?> updateStatusRentingRequest(@PathVariable long rentingRequestId, @Valid @RequestBody RentingRequestStatusDto rentingRequestStatusDto) {
        BindingResult bindingResult = new DataBinder(rentingRequestStatusDto).getBindingResult();
        rentingRequestValidator.validate(rentingRequestStatusDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            RentingRequest updatedRequest = rentingRequestService.getRentingRequest(rentingRequestService.updateRentingRequestStatus(rentingRequestId, rentingRequestStatusDto.getStatus()).getId());
            if (updatedRequest != null) {
                return ResponseEntity.ok(updatedRequest);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
