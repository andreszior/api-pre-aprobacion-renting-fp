/*
package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class InvestmentRuleTest {

    private InvestmentRule sut;

    private RentingRequest request;

    private static final double LIMIT_VALUE = 80000.0;

    @BeforeEach
    void setUp() {
        sut = new InvestmentRule();
        request = RentingRequest.builder().build();
        request.setClientId(1L);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenInvestmentIsSmallerThanLimit() {
        setVehicles(LIMIT_VALUE - 10000);
        Assertions.assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenInvestmentIsLimitValue() {
        setVehicles(LIMIT_VALUE);
        Assertions.assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenInvestmentIsGreaterThanLimitValue() {
        setVehicles(LIMIT_VALUE + 10000);
        Assertions.assertFalse(sut.approve(request));
    }

    private void setVehicles(double amount) {
        List<Vehicle> vehicleDtoList = new ArrayList<>();
        Vehicle dto = new Vehicle();
        dto.setPrice(amount);
        vehicleDtoList.add(dto);
        request.setVehicles(vehicleDtoList);
    }

}
*/
