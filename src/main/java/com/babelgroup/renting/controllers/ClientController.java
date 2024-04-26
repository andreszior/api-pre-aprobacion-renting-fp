
package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
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
    private final CountryService countryService;
    private final ProvinceService provinceService;
    private final ClientValidator clientValidator;


    public ClientController(ClientService clientService, ClientValidator clientValidator,
                            CountryService countryService, ProvinceService provinceService) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.countryService = countryService;
        this.provinceService = provinceService;
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

            if (clientService.clientExists(clientDto.getDni())) {
                return new ResponseEntity<>("El cliente ya existe", HttpStatus.BAD_REQUEST);
            }

            if (!isFreelance(clientDto) && !isSalaried(clientDto)) {
                return new ResponseEntity<>("El cliente debe ser autónomo o asalariado", HttpStatus.BAD_REQUEST);
            }

            client = buildClientEntity(clientDto);

            if (client.getCountry() == null || client.getProvinceCode() == null) {
                return new ResponseEntity<>("El país o la provincia no existen o son erroneas", HttpStatus.BAD_REQUEST);
            }

            clientService.createClient(client);
            Employee employee = buildEmployeeEntity(client);
            clientService.createEmployee(employee);

            createSalariedEntries(clientDto, employee);
            createFreelanceEntries(clientDto, employee);


        } catch (Exception e) {
            Log.logError(e.getMessage(), e);
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Log.logInfo("Cliente creado correctamente.");
        return new ResponseEntity<>(client.getId(), HttpStatus.CREATED);
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
            } else {
                return new ResponseEntity<>(clientUpdateDto, HttpStatus.NOT_FOUND);
            }
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
        boolean deleted = clientService.deleteClient(clientId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Client buildClientEntity(ClientDto clientDto) {
        return Client.builder()
                .dni(clientDto.getDni())
                .name(clientDto.getName())
                .rating(0)
                .birthdate(clientDto.getBirthdate())
                .lastnameFirst(clientDto.getLastnameFirst())
                .lastnameSecond(clientDto.getLastnameSecond())
                .country(countryService.getCountry(clientDto.getCountry()))
                .provinceCode(provinceService.getProvince(clientDto.getProvinceCode()))
                .build();
    }

    public Employee buildEmployeeEntity(Client client) {
        return Employee.builder()
                .id(client.getId())
                .clientId(client.getId())
                .build();
    }

    public void createSalariedEntries(ClientDto clientDto, Employee employee) {
        if (isSalaried(clientDto)) {
            Salaried salaried = Salaried.builder()
                    .jobAntiquity(clientDto.getJobAntiquity())
                    .employeeId(employee.getId())
                    .cif(clientDto.getCompanyCif())
                    .build();

            clientService.createSalaried(salaried);
            Log.logInfo("Registro cliente asalariado creado correctamente.");

            SalariedIncome salaryIncome = SalariedIncome.builder()
                    .netIncome(clientDto.getNetIncome())
                    .salariedId(salaried.getId())
                    .salaryYear(clientDto.getSalaryYear())
                    .build();

            clientService.createSalariedIncome(salaryIncome);
            Log.logInfo("Registro ingresos asalariado creado correctamente.");
        }
    }

    public void createFreelanceEntries(ClientDto clientDto, Employee employee){
        if (isFreelance(clientDto)) {
            Freelance freelance = Freelance.builder()
                    .netIncome(clientDto.getNetIncome())
                    .grossIncome(clientDto.getGrossIncome())
                    .yearSalary(clientDto.getSalaryYear())
                    .employeeId(employee.getId())
                    .build();

            clientService.createFreelance(freelance);
            Log.logInfo("Registro cliente autónomo creado correctamente.");
        }
    }

    public Boolean isFreelance(ClientDto clientDto) {
        return clientDto.getGrossIncome() != null && clientDto.getNetIncome() != null && clientDto.getSalaryYear() != null;
    }

    public Boolean isSalaried(ClientDto clientDto) {
        return clientDto.getJobAntiquity() != null && clientDto.getCompanyCif() != null && clientDto.getNetIncome() != null && clientDto.getSalaryYear() != null;
    }

}
