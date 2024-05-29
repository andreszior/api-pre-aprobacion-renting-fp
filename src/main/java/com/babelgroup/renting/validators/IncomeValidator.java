package com.babelgroup.renting.validators;

import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import com.babelgroup.renting.logger.Log;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class IncomeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IncomeDTO income = (IncomeDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "netIncome", "netIncome.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossIncome", "grossIncome.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salaryYear", "salaryYear.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobAntiquity", "jobAntiquity.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isFreelance", "isFreelance.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyCif", "companyCif.empty");

        if(income.getCompanyCif() != null && income.getCompanyCif().length() != 9){
            errors.rejectValue("companyCif", "companyCif.length", "El CIF de la empresa debe tener 9 caracteres");
        }

        if(income.getNetIncome() != null && income.getNetIncome() < 0){
            errors.rejectValue("netIncome", "netIncome.invalid", "Los ingresos netos no pueden ser negativos");
        }

        if(income.getSalaryYear() != null && income.getSalaryYear() < 0){
            errors.rejectValue("salaryYear", "salaryYear.invalid", "El año del salario no puede ser negativo");
        }

        if(income.getJobAntiquity() != null && (income.getJobAntiquity().after(new Date()))) {
            errors.rejectValue("jobAntiquity", "jobAntiquity.invalid", "La fecha de antigüedad laboral no puede ser posterior a la fecha actual");
        }
    }

}
