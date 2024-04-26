package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AssignorCreditorRuleTest {

    private ClientMapper mapper;
    private AssignorCreditorRule sut;

    @BeforeEach
    void setUp(){
        mapper = Mockito.mock(ClientMapper.class);
        sut = new AssignorCreditorRule(mapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenIsRentingCategory(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(4L);

        when(mapper.getAssignorOrCreditor(request.getClientId())).thenReturn(1);

        assertTrue(sut.denegate(request));
    }


    @Test
    void testApprove_ShouldReturnTrue_WhenIsFinanceCategory(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(5L);

        when(mapper.getAssignorOrCreditor(request.getClientId())).thenReturn(1);

        assertTrue(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenIsNotCreditorOrAssignor(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(10L);

        when(mapper.getAssignorOrCreditor(request.getClientId())).thenReturn(0);

        assertFalse(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenIsDoesNotHaveDebt(){

        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        when(mapper.getAssignorOrCreditor(request.getClientId())).thenReturn(0);

        assertFalse(sut.denegate(request));

    }
}