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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientId", "clientId.empty", "El campo cliente no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "effectiveDateRenting", "effectiveDateRenting.empty", "El campo fecha de inicio de vigencia no puede estar vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicles", "vehicles.empty", "El campo vehículos no puede estar vacío");
    }
}
