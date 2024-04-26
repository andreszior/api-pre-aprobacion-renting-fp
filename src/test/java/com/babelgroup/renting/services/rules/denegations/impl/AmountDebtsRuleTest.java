package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AmountDebtsRuleTest {

    private ClientMapper mapper;
    private AmountDebtsRule sut;

    @BeforeEach
    void setSut(){
        mapper = Mockito.mock(ClientMapper.class);
        sut = new AmountDebtsRule(mapper);
    }

    @Test
    void testDenegate_ShouldReturnFalse_WhenAmountDebtsLessThanTotalFee() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        request.setFee(50.0);

        when(mapper.getAmountDebt(request.getClientId())).thenReturn(49.0);
        assertFalse(sut.denegate(request));
    }

    @Test
    void testDenegate_ShouldReturnTrue_WhenAmountDebtsGreaterThanTotalFee() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        request.setFee(50.0);

        when(mapper.getAmountDebt(request.getClientId())).thenReturn(100.0);

        assertTrue(sut.denegate(request));
    }

    @Test
    void testDenegate_ShouldReturnTrue_WhenAmountDebtsEqualToTotalFee() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        request.setFee(50.0);

        when(mapper.getAmountDebt(request.getClientId())).thenReturn(50.0);

        assertTrue(sut.denegate(request));
    }
}
