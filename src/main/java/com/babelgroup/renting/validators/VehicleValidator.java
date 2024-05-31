package com.babelgroup.renting.validators;

import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VehicleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VehicleDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VehicleDto vehicle = (VehicleDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cylinderCapacity", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "power", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfSeats", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baseFee", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "clientId.empty", "El campo cliente no puede estar vacío");


        if(vehicle.getPrice() != null && vehicle.getPrice() <= 0){
            errors.rejectValue("price", "price.invalid", "El precio no puede ser negativo");
        }

        if(vehicle.getCylinderCapacity() != null && vehicle.getCylinderCapacity() <= 0){
            errors.rejectValue("cylinderCapacity", "cylinderCapacity.invalid", "La cilindrada no puede ser cero o negativa");
        }

        if(vehicle.getPower() != null && vehicle.getPower() <= 0){
            errors.rejectValue("power", "power.invalid", "La potencia no puede ser cero o negativa");
        }

        if(vehicle.getNumberOfSeats() != null && vehicle.getNumberOfSeats() <= 0){
            errors.rejectValue("numberOfSeats", "numberOfSeats.invalid", "El número de plazas no puede ser cero o negativo");
        }

        if(vehicle.getBaseFee() != null && vehicle.getBaseFee() <= 0){
            errors.rejectValue("baseFee", "baseFee.invalid", "La cuota base no puede ser cero o negativa");
        }

    }
}
