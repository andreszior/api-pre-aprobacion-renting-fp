package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.exceptions.RequestValidationException;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.services.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/income")
@Tag(
        name = "Income",
        description = "Este controlador tiene la función de añadir el tiempo de renta a los cliente."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta.", content = @Content(schema = @Schema(implementation = BindingResult.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content(schema = @Schema(implementation = String.class, example = "Error interno del servidor.")))
})public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("")
    @Operation(summary = "Registrar una nueva renta de cliente.",
            description = "Dada una información de entrada, crea nueva renta de cliente.")
    @ApiResponse(responseCode = "201", description = "Renta de cliente creada correctamente.",
            content = @Content(schema = @Schema(implementation = Integer.class, example = "30")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos del id del cliente y la renta del cliente.", required = true,
            content = @Content(schema = @Schema(implementation = IncomeDto.class)))
    public ResponseEntity<?> registerIncome(@Valid @RequestBody IncomeDto incomeDto, BindingResult bindingResult) {
        try {
            incomeService.createIncome(incomeDto);
        }
        catch (Exception e) {
            Log.logError(e.getMessage(), e);
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Log.logInfo("Renta creada correctamente.");
        return new ResponseEntity<>(income.getId(), HttpStatus.CREATED);
    }



    @GetMapping("/{clientId}")
    @Operation(summary = "Recupera el income de cliente",
        description = "Dado un id de cliente, recupera la renta")
    @ApiResponse(responseCode = "200", description = "Renta de cliente recuperada correctamente.",
        content = @Content(schema = @Schema(implementation = Long.class, example = "30")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    public ResponseEntity<?> getIncome(@PathVariable long clientId, BindingResult bindingResult) {
        try {
            List<Income> incomesClient = incomeService.getIncomes(clientId);
            return new ResponseEntity<>(incomesClient, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
