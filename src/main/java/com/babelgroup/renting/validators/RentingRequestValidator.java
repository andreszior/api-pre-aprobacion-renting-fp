package com.babelgroup.renting.validators;


import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class RentingRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz){
        return RentingRequestDto.class.equals(clazz);
    }

    @Override
    public void validate (Object target, Errors errors) {
        RentingRequestDto rentingRequestDto = (RentingRequestDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientId", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentingRequestDate", "rentingRequestDate.empty", "El campo fecha de solicitud no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "effectiveDateRenting", "effectiveDateRenting.empty", "El campo fecha de inicio de vigencia no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resolutionDate", "resolutionDate.empty", "El campo fecha de resolución no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfVehicles", "numberOfVehicles.empty", "El campo cantidad de vehículos no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicles", "vehicles.empty", "El campo vehículos no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "investment", "investment.empty", "El campo inversión total no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fee", "fee.empty", "El campo cuota no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deadline", "deadline.empty", "El campo plazo no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resolution", "resolution.empty", "El campo estado de la solicitud no puede estar vacío");

        if (rentingRequestDto.getNumberOfVehicles() < 1) {
            errors.rejectValue("numberOfVehicles", "numberOfVehicles.invalid", "La cantidad de vehículos solicitados debe ser mayor a 0");
        }

        if (rentingRequestDto.getInvestment() < 0) {
            errors.rejectValue("investment", "investment.invalid", "La inversión no puede ser negativa");
        }

        if (rentingRequestDto.getFee() < 0) {
            errors.rejectValue("fee", "fee.invalid", "La cuota no puede ser negativa");
        }

        if (rentingRequestDto.getDeadline() < 1) {
            errors.rejectValue("deadline", "deadline.invalid", "El plazo debe ser mayor a 0");
        }

        if (!rentingRequestDto.getResolution().equalsIgnoreCase("Aprobada") &&
                !rentingRequestDto.getResolution().equalsIgnoreCase("Denegada") &&
                !rentingRequestDto.getResolution().equalsIgnoreCase("Aprobada con garantías") &&
                !rentingRequestDto.getResolution().equalsIgnoreCase("Pendiente a revisar") &&
                !rentingRequestDto.getResolution().equalsIgnoreCase("Preaprobada") &&
                !rentingRequestDto.getResolution().equalsIgnoreCase("Predenegada")) {
            errors.rejectValue("resolution", "resolution.invalid", "El estado de la solicitud no es válido");
        }
    }
}
