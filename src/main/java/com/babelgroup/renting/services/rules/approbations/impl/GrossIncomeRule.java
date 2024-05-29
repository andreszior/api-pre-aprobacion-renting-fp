package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class GrossIncomeRule implements ApprobationRule {

    private final IncomeMapper mapper;

    @Override
    public boolean approve(RentingRequest request) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());
        int year = cal.get(Calendar.YEAR);

        boolean isFreelance = mapper.isFreelance(request.getClientId(), year) > 0;

        if (isFreelance) {
            long grossIncome = mapper.getGrossIncome(request.getClientId(), year);
            double investment = request.getInvestment();
            return investment <= grossIncome * 3;
        }

        return true;
    }
}
