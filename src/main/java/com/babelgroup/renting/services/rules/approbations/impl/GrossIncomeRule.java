package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class GrossIncomeRule implements ApprobationRule {
    private final EmployeeMapper mapper;

    @Override
    public boolean approve(RentingRequest request) {
        double inversion = request.getInvestment();

        // Se usa Calendar porque getYear esta deprecada
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());

        long grossIncome = mapper.getGrossIncome(request.getClientId(),
                cal.get(Calendar.YEAR));

        // Si tiene algún ingreso bruto en los últimos 3 años es que es autonomo
        boolean isFreelance = mapper.isFreelance(request.getClientId(),
                cal.get(Calendar.YEAR)) > 0;

        if(isFreelance){
            return inversion <= grossIncome * 3;
        }
        return true;
    }
}
