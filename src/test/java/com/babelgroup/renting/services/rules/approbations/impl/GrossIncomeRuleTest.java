package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GrossIncomeRuleTest {

    private IncomeMapper mapper;
    private GrossIncomeRule sut;
    private final static int CURRENT_YEAR = 2024;

    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(IncomeMapper.class);
        sut = new GrossIncomeRule(mapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenInversionLowerThanGrossIncome() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(3L);
        request.setInvestment(100.0);
        request.setRentingRequestDate(new Date(System.currentTimeMillis()));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());

        when(mapper.getGrossIncome(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(10000L);
        when(mapper.isFreelance(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(1);

        assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenInversionEqualToGrossIncome() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(5L);
        request.setInvestment(30000.0);
        request.setRentingRequestDate(new Date(System.currentTimeMillis()));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());

        when(mapper.getGrossIncome(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(10000L);
        when(mapper.isFreelance(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(1);

        assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenInversionHigherThanGrossIncome() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        request.setInvestment(3000000.0);
        request.setRentingRequestDate(new Date(System.currentTimeMillis()));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());

        when(mapper.getGrossIncome(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(10500L);
        when(mapper.isFreelance(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(1);

        assertFalse(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenIsNotFreelance() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(4L);
        request.setInvestment(300.0);
        request.setRentingRequestDate(new Date(System.currentTimeMillis()));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(request.getRentingRequestDate());

        when(mapper.isFreelance(request.getClientId(), cal.get(Calendar.YEAR))).thenReturn(0);

        assertTrue(sut.approve(request));
    }
}