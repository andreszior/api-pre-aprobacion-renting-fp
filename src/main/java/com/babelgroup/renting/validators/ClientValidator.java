package com.babelgroup.renting.validators;

import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.logger.Log;
import jakarta.validation.Validation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class ClientValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientDto client = (ClientDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastnameFirst", "lastnameFirst.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastnameSecond", "lastnameSecond.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "dni.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthdate", "birthdate.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "provinceCode", "provinceCode.empty");

        if (client.getDni().length() != 9) {
            errors.rejectValue("dni", "dni.length", "El DNI debe tener 9 caracteres");
        }

        if(client.getCountry().length() != 3){
            errors.rejectValue("country", "country.length", "El código de país debe tener 3 caracteres");
        }

        if(client.getProvinceCode().length() >= 2){
            Log.logInfo(String.valueOf(client.getProvinceCode().length()));
            errors.rejectValue("provinceCode", "provinceCode.length", "El código de provincia debe tener 2 caracteres");
        }

        try{
            if (client.getBirthdate() != null && (client.getBirthdate().after(new Date()))) {
                errors.rejectValue("birthdate", "birthdate.invalid", "La fecha de nacimiento no puede ser posterior a la fecha actual");

            }
        } catch (Exception e) {
            errors.rejectValue("birthdate", "birthdate.invalid", "La fecha de nacimiento no es válida");
        }








    }
}
