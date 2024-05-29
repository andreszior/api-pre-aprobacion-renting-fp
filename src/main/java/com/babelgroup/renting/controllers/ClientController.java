
package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.exceptions.ClientNotFreelanceOrSalariedException;
import com.babelgroup.renting.exceptions.ClientsAlreadyExistsException;
import com.babelgroup.renting.exceptions.CountryOrProvinceException;
import com.babelgroup.renting.exceptions.RequestValidationException;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.ProvinceService;
import com.babelgroup.renting.validators.ClientValidator;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Tag(
        name = "Cliente",
        description = "Este controlador tiene la función de tratar los clientes de renting."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta.", content = @Content(schema = @Schema(implementation = BindingResult.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content(schema = @Schema(implementation = String.class, example = "Error interno del servidor.")))
})
public class ClientController {
    private final ClientService clientService;

    private final ClientValidator clientValidator;


    public ClientController(ClientService clientService, ClientValidator clientValidator,
                            CountryService countryService, ProvinceService provinceService) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;

    }

    @PostMapping("")
    @Operation(summary = "Registrar un cliente nuevo.",
            description = "Dada una información de entrada, crea un cliente nuevo.")
    @ApiResponse(responseCode = "201", description = "Cliente creado correctamente.",
            content = @Content(schema = @Schema(implementation = Integer.class, example = "30")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos de cliente requeridos para el registro.", required = true,
            content = @Content(schema = @Schema(implementation = ClientDto.class)))
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDto clientDto, BindingResult bindingResult) {
        clientValidator.validate(clientDto, bindingResult);
        Client client;
        try {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
            }
            validation(clientDto);
            client = clientService.createClient(clientDto);
        }
        catch (RequestValidationException rve){
            return new ResponseEntity<>(rve.getHttpMessage(), rve.getHttpStatus());
        }
        catch (Exception e) {
            Log.logError(e.getMessage(), e);
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Log.logInfo("Cliente creado correctamente.");
        return new ResponseEntity<>(client.getId(), HttpStatus.CREATED);
    }

    private void validation(ClientDto clientDto) throws RequestValidationException {

        if (clientService.clientExists(clientDto.getDni())) {
            throw new ClientsAlreadyExistsException("El cliente ya existe");
        }

        if (clientDto.getCountry() == null || clientDto.getProvinceCode() == null) {
            throw new CountryOrProvinceException("El cliente no tiene provincia o país");
        }
    }

    @PutMapping("/{clientId}")
    @Operation(summary = "Actualizar los datos de un cliente.",
            description = "Actualiza un cliente ya existente con nuevos datos.")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado súper exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos a actualizar del cliente.", required = true,
            content = @Content(schema = @Schema(implementation = ClientUpdateDto.class)))
    public ResponseEntity<?> updateClient(@PathVariable long clientId,
                                          @org.springframework.web.bind.annotation.RequestBody ClientUpdateDto clientUpdateDto) {
        try {
            Boolean updated = clientService.updateClient(clientId, clientUpdateDto);
            if (Boolean.TRUE.equals(updated)) {
                return new ResponseEntity<>(clientUpdateDto, HttpStatus.OK);
            }
            return new ResponseEntity<>(clientUpdateDto, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{clientId}")
    @Operation(summary = "Eliminar un cliente.",
            description = "Dado el identificador de un cliente de entrada, elimina el cliente en concreto.")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente.")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado.")
    public ResponseEntity<Void> deleteClient(@PathVariable long clientId){
        Boolean deleted = clientService.deleteClient(clientId);
        if (Boolean.TRUE.equals(deleted)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
