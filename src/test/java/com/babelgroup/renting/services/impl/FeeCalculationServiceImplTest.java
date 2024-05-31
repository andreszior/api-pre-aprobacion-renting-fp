/*
package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeeCalculationServiceImplTest {

    private FeeCalculationServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new FeeCalculationServiceImpl();
    }

    @Test
    void testCalculateFee_ShouldReturnBaseFee_WhenDurationIsBaseMonthDuration() {
        //Given
        double baseFee = 100.0;
        int duration = 12;

        RentingRequest rentingRequest = RentingRequest.builder().build();
        rentingRequest.setDeadline(12);

        Vehicle vehicle = new Vehicle();
        vehicle.setBaseFee(100.0);
        rentingRequest.setVehicles(List.of(vehicle));

        double expected = baseFee;
        //When
        double actual = sut.calculateFee(rentingRequest);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testCalculateFee_ShouldReturnBaseFeePlusPenalty_WhenDurationIsLessThanBaseMonthDuration() {
        //Given
        double baseFee = 100.0;
        int duration = 10;

        RentingRequest rentingRequest = RentingRequest.builder().build();
        rentingRequest.setDeadline(10);

        Vehicle vehicle = new Vehicle();
        vehicle.setBaseFee(100.0);
        rentingRequest.setVehicles(List.of(vehicle));

        double expected = baseFee + baseFee * 0.1 * (12 - 10);
        //When
        double actual = sut.calculateFee(rentingRequest);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testCalculateFee_ShouldReturnBaseFeeMinusDiscount_WhenDurationIsMoreThanBaseMonthDuration() {
        //Given
        double baseFee = 100.0;
        int duration = 15;

        RentingRequest rentingRequest = RentingRequest.builder().build();
        rentingRequest.setDeadline(15);

        Vehicle vehicle = new Vehicle();
        vehicle.setBaseFee(100.0);
        rentingRequest.setVehicles(List.of(vehicle));

        double expected = baseFee - baseFee * 0.03 * (15 - 12);
        //When
        double actual = sut.calculateFee(rentingRequest);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testCalculateFee_ShouldReturnBaseFeeMinusMaxDiscount_WhenDurationIsMoreThanBaseMonthDuration() {
        //Given
        double baseFee = 100.0;
        int duration = 20;

        RentingRequest rentingRequest = RentingRequest.builder().build();
        rentingRequest.setDeadline(20);

        Vehicle vehicle = new Vehicle();
        vehicle.setBaseFee(100.0);
        rentingRequest.setVehicles(List.of(vehicle));

        double expected = baseFee - baseFee * 0.2;
        //When
        double actual = sut.calculateFee(rentingRequest);
        //Then
        assertEquals(expected, actual);
    }
}*/
