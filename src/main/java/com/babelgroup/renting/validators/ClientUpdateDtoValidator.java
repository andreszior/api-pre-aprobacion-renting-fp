package com.babelgroup.renting.validators;

import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.logger.Log;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class ClientUpdateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClientUpdateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientUpdateDto client = (ClientUpdateDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastnameFirst", "lastnameFirst.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastnameSecond", "lastnameSecond.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "provinceCode", "provinceCode.empty");

        if(client.getCountry().length() != 3){
            errors.rejectValue("country", "country.length", "El código de país debe tener 3 caracteres");
        }

        if(client.getProvinceCode().length() >= 2){
            Log.logInfo(String.valueOf(client.getProvinceCode().length()));
            errors.rejectValue("provinceCode", "provinceCode.length", "El código de provincia debe tener 2 caracteres");
        }
    }
}
