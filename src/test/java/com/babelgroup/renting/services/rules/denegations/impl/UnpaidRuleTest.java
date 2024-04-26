package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UnpaidRuleTest {

    private UnpaidRule sut;
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp(){
        clientMapper = Mockito.mock(ClientMapper.class);

        sut = new UnpaidRule(clientMapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenClientHasUnpaids(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        when(clientMapper.getNumberOfUnpaids(anyLong())).thenReturn(1);
        //When
        boolean actual = sut.denegate(request);
        //Then
        assertTrue(actual);
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenClientHasNoUnpaids(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        when(clientMapper.getNumberOfUnpaids(anyLong())).thenReturn(0);
        //When
        boolean actual = sut.denegate(request);
        //Then
        assertFalse(actual);
    }
}