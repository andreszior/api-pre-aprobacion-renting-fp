package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.mappers.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class NetIncomeRuleTest {

    private NetIncomeRule sut;
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        employeeMapper = Mockito.mock(EmployeeMapper.class);
        when(employeeMapper.getAverageSalary(anyLong(), anyInt())).thenReturn(10000L);

        sut = new NetIncomeRule(employeeMapper);
    }

    @Test
    void testApprove_shouldReturnTrue_WhenInversionIsLessThanAverageSalary() {
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        request.setRentingRequestDate(new Date());
        Vehicle Vehicle = new Vehicle();
        Vehicle.setPrice(5000.0);
        request.setVehicles(List.of(Vehicle));

        //When
        boolean actual = sut.approve(request);

        //Then
        assertTrue(actual);
    }

    @Test
    void testApprove_shouldReturnFalse_WhenInversionIsGreaterThanAverageSalary() {
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        request.setRentingRequestDate(new Date());
        Vehicle Vehicle = new Vehicle();
        Vehicle.setPrice(15000.0);
        request.setVehicles(List.of(Vehicle));

        //When
        boolean actual = sut.approve(request);

        //Then
        assertFalse(actual);
    }

    @Test
    void testApprove_shouldReturnTrue_WhenInversionIsEqualToAverageSalary() {
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        request.setRentingRequestDate(new Date());
        Vehicle Vehicle = new Vehicle();
        Vehicle.setPrice(10000.0);
        request.setVehicles(List.of(Vehicle));

        //When
        boolean actual = sut.approve(request);

        //Then
        assertTrue(actual);
    }
}