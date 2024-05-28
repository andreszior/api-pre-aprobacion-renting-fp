package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;

@RequiredArgsConstructor
@Service
public class EmploymentSeniorityRule implements ApprobationRule {

    private final IncomeMapper incomeMapper;

    @Override
    public boolean approve(RentingRequest request) {
        long startYear = incomeMapper.getEploymentYear(request.getClientId());
        return Math.abs(Year.now().getValue() - startYear) >= 3;
    }
}
