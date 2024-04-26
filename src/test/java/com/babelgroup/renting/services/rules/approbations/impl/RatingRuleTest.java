package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RatingRuleTest {

    private ClientMapper mapper;
    private RatingRule sut;

    @BeforeEach
    void setSut(){
        mapper = Mockito.mock(ClientMapper.class);
        sut = new RatingRule(mapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenRatingLessThan5() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        when(mapper.getRating(request.getClientId())).thenReturn("4");

        assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenRatingEqualTo5() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        // Simular un valor de calificación igual a 5
        when(mapper.getRating(request.getClientId())).thenReturn("5");

        assertFalse(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenRatingGreaterThan5() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        // Simular un valor de calificación mayor que 5
        when(mapper.getRating(request.getClientId())).thenReturn("6");

        assertFalse(sut.approve(request));
    }

}
