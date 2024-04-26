package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.services.FeeCalculationService;

public class FeeCalculationServiceImpl implements FeeCalculationService {

    private static final int BASE_MONTH_DURATION = 12;
    private static final double PENALTY_RATE = 0.1;
    private static final double DISCOUNT_RATE = 0.03;
    private static final double MAX_DISCOUNT = 0.2;

    @Override
    public double calculateFee(RentingRequest rentingRequest) {
        int duration = rentingRequest.getDeadline();
        double fee = rentingRequest.getVehicles().stream().map(Vehicle::getBaseFee).reduce(0.0, Double::sum);

        if (duration < BASE_MONTH_DURATION) {
            fee += fee * (PENALTY_RATE * (BASE_MONTH_DURATION - duration));
        }
        else if (duration > BASE_MONTH_DURATION) {
            double discount = DISCOUNT_RATE * (duration - BASE_MONTH_DURATION);
            fee -= fee * (Math.min(discount, MAX_DISCOUNT));
        }
        return fee;
    }
}
