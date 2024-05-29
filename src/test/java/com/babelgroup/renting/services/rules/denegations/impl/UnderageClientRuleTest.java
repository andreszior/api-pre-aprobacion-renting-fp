package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UnderageClientRuleTest {

    private ClientMapper mapper;
    private UnderageClientRule sut;

    @BeforeEach
    void setUp(){
        mapper = Mockito.mock(ClientMapper.class);
        sut = new UnderageClientRule(mapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenIsUnderage(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(4L);

        when(mapper.getAgeClient(request.getClientId())).thenReturn(17);

        assertTrue(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenIsJust18(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(7L);

        when(mapper.getAgeClient(request.getClientId())).thenReturn(18);

        assertFalse(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenIsWayTooOld(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(10L);

        when(mapper.getAgeClient(request.getClientId())).thenReturn(55);

        assertFalse(sut.denegate(request));
    }

}