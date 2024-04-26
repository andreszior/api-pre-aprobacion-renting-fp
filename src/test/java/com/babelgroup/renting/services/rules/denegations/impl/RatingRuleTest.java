package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RatingRuleTest {

    private ClientMapper mapper;

    private RatingRule sut;


    @BeforeEach
    void setSut(){
        mapper = Mockito.mock(ClientMapper.class);
        sut = new RatingRule(mapper);
    }

    @Test
    void testDenegate_ShouldReturnFalse_WhenRatingLessThan6() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        when(mapper.getRating(request.getClientId())).thenReturn("5");

        assertFalse(sut.denegate(request));
    }

    @Test
    void testDenegate_ShouldReturnTrue_WhenRatingEqualTo6() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        when(mapper.getRating(request.getClientId())).thenReturn("6");

        assertTrue(sut.denegate(request));
    }

    @Test
    void testDenegate_ShouldReturnTrue_WhenRatingGreaterThan6() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        // Simular un valor de calificación mayor que 6
        when(mapper.getRating(request.getClientId())).thenReturn("7");

        assertTrue(sut.denegate(request));
    }
}
