package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ClientDeadlineAgeRuleTest {

    private ClientDeadlineAgeRule sut;
    private ClientMapper clientMapper;
    private RentingRequest request;

    @BeforeEach
    void setUp() {
        clientMapper = Mockito.mock(ClientMapper.class);

        sut = new ClientDeadlineAgeRule(clientMapper);

        request = RentingRequest.builder().build();
        request.setClientId(1L);
    }

    @Test
    void testDenegation_ShouldReturnTrue_WhenClientIsOlderTHanEighty() {
        request.setDeadline(12);
        when(clientMapper.getAgeClient(anyLong())).thenReturn(81);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testDenegation_ShouldReturnFalse_WhenClientIsYoungAndDDeadlineIsShort() {
        request.setDeadline(12);
        when(clientMapper.getAgeClient(anyLong())).thenReturn(17);

        Assertions.assertFalse(sut.denegate(request));
    }

    @Test
    void testDenegation_ShouldReturnTrue_WhenClientIsYoungAndDDeadlineTooLong() {
        request.setDeadline(800);
        when(clientMapper.getAgeClient(anyLong())).thenReturn(17);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testDenegation_ShouldReturnTrue_WhenClientIsOlderTHanEightyAndDDeadlineTooLong() {
        request.setDeadline(720);
        when(clientMapper.getAgeClient(anyLong())).thenReturn(81);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testDenegation_ShouldReturnFalse_WhenClientIsOldAndDeadlineIsShort() {
        request.setDeadline(6);
        when(clientMapper.getAgeClient(anyLong())).thenReturn(70);

        Assertions.assertFalse(sut.denegate(request));
    }

}
