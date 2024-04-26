package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.RentingRequest;

public interface FeeCalculationService {

    double calculateFee(RentingRequest rentingRequest);
}
