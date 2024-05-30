package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class DebtRuleTest {

    private ClientMapper mapper;
    private DebtRule sut;

    private RentingRequest request;

    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(ClientMapper.class);
        sut = new DebtRule(mapper);
        request = RentingRequest.builder().build();
        request.setClientId(1L);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenDebtSmallerThanFee() {
        request.setDeadline(12);
        request.setFee(200.0);

        when(mapper.getAmountDebt(request.getClientId())).thenReturn(100.0);

        Assertions.assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenDebtBiggerThanFee() {
        request.setDeadline(12);
        request.setFee(50.0);

        when(mapper.getAmountDebt(request.getClientId())).thenReturn(100.0);

        Assertions.assertFalse(sut.approve(request));
    }

}
