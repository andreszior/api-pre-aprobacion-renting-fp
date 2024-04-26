package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class NetIncomeRule implements ApprobationRule {

    private final EmployeeMapper mapper;

    @Override
    public boolean approve(RentingRequest request) {
        double inversion = 0;
        for (Vehicle vehicleDto : request.getVehicles()){
            inversion += vehicleDto.getPrice();
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());
        long averageSalary = mapper.getAverageSalary(request.getClientId(),
                cal.get(Calendar.YEAR));
        return inversion <= averageSalary;
    }
}
