package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import org.springframework.stereotype.Service;

@Service
public class InvestmentRule implements ApprobationRule {

    private static final double LIMIT_VALUE = 80000.0;

    @Override
    public boolean approve(RentingRequest request) {
        double inversion = 0;
        for (Vehicle vehicleDto : request.getVehicles()) {
            inversion += vehicleDto.getPrice();
        }
        return inversion <= LIMIT_VALUE;
    }
}
