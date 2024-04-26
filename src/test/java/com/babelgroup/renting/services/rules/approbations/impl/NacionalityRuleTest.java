package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NacionalityRuleTest {

    private NacionalityRule sut;
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = mock(ClientMapper.class);
        sut = new NacionalityRule(clientMapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenNacionalityIsES() {
        // Arrange
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        when(clientMapper.getNacionalidad(request.getClientId())).thenReturn("ES");
        // Act
        boolean result = sut.approve(request);
        // Assert
        assertTrue(result);
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenNacionalityIsNotES() {
        // Arrange
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        when(clientMapper.getNacionalidad(request.getClientId())).thenReturn("FR");
        // Act
        boolean result = sut.approve(request);
        // Assert
        assertFalse(result);
    }
}