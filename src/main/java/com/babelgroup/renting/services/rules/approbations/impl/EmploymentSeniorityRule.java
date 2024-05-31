package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmploymentSeniorityRule implements ApprobationRule {

    private final IncomeMapper incomeMapper;

    @Override
    public boolean approve(RentingRequest request) {
        Date startYear = incomeMapper.getEploymentYear(request.getClientId());
        LocalDate startDate = startYear.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int seniorityYears = Period.between(startDate, currentDate).getYears();
        return seniorityYears >= 3;
    }
}


