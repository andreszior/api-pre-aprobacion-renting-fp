package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GuarantorVerificationRuleTest {

    private GuarantorVerificationRule sut;
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = Mockito.mock(ClientMapper.class);
        sut = new GuarantorVerificationRule(clientMapper);
    }

    @Test
    void testApprove_whenClientIsNotNew_shouldReturnTrue() {
        // Arrange
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        Mockito.when(clientMapper.isNewClient(1L)).thenReturn(true);

        // Act
        boolean result = sut.approve(request);

        // Assert
        assertTrue(result);
    }

    @Test
    void testApprove_whenClientIsNewAndIsNotGuarantor_shouldReturnTrue() {
        // Arrange
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        Mockito.when(clientMapper.isNewClient(1L)).thenReturn(false);
        Mockito.when(clientMapper.isGuarantor(1L)).thenReturn(false);

        // Act
        boolean result = sut.approve(request);

        // Assert
        assertTrue(result);
    }

    @Test
    void testApprove_whenClientIsNewAndIsGuarantor_shouldReturnFalse() {
        // Arrange
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        Mockito.when(clientMapper.isNewClient(1L)).thenReturn(true);
        Mockito.when(clientMapper.isGuarantor(1L)).thenReturn(true);

        // Act
        boolean result = sut.approve(request);

        // Assert
        assertFalse(result);
    }
}