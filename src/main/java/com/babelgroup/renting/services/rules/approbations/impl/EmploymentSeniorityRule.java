package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmploymentSeniorityRule implements ApprobationRule {

    private final IncomeMapper incomeMapper;

    @Override
    public boolean approve(RentingRequest request) {
        Date startYear = incomeMapper.getEploymentYear(request.getClientId());
        float seniority = Duration.between(LocalDate.now(), startYear.toInstant()).toDays()/365f;
        return seniority >= 3;
    }
}
