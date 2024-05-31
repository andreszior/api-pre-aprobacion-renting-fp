package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.Income;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import com.babelgroup.renting.exceptions.RequestValidationException;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.services.IncomeService;
import com.babelgroup.renting.validators.IncomeValidator;
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

import java.util.List;

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
    private final IncomeValidator incomeValidator;

    public IncomeController(IncomeService incomeService, IncomeValidator incomeValidator) {
        this.incomeService = incomeService;
        this.incomeValidator = incomeValidator;
    }

    @PostMapping("")
    @Operation(summary = "Registrar una nueva renta de cliente.",
            description = "Dada una información de entrada, crea nueva renta de cliente.")
    @ApiResponse(responseCode = "201", description = "Renta de cliente creada correctamente.",
            content = @Content(schema = @Schema(implementation = Integer.class, example = "30")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos del id del cliente y la renta del cliente.", required = true,
            content = @Content(schema = @Schema(implementation = IncomeDTO.class)))
    public ResponseEntity<?> registerIncome(@Valid @RequestBody IncomeDTO incomeDto, BindingResult bindingResult) {
        incomeValidator.validate(incomeDto, bindingResult);
        Long id;
        try {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
            }

            id = incomeService.createIncome(incomeDto);
            if (id == null) throw new Exception();
        } catch (RequestValidationException rve){
            return new ResponseEntity<>(rve.getHttpMessage(), rve.getHttpStatus());
        }
        catch (Exception e) {
            Log.logError(e.getMessage(), e);
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Log.logInfo("Renta creada correctamente.");
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Recupera las rentas registradas del cliente",
        description = "Dado un id de cliente, recupera sus rentas")
    @ApiResponse(responseCode = "200", description = "Rentas de cliente recuperadas correctamente.",
        content = @Content(schema = @Schema(implementation = Long.class, example = "30")))
    public ResponseEntity<?> getIncomes(@PathVariable long clientId) {
        try {
            List<IncomeDTO> incomesList = incomeService.getIncomes(clientId);
            return new ResponseEntity<>(incomesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
