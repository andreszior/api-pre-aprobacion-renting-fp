/*
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

    private Date oldBirthdate;
    private Date oldBirthdate_2;
    private Date youngBirthdate;
    private RentingRequest request;

    @BeforeEach
    void setUp() {
        clientMapper = Mockito.mock(ClientMapper.class);

        sut = new ClientDeadlineAgeRule(clientMapper);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1940);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        oldBirthdate = calendar.getTime();
        calendar.set(Calendar.YEAR, 1950);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        oldBirthdate_2 = calendar.getTime();

        Calendar youngCalendar = Calendar.getInstance();
        youngCalendar.set(Calendar.YEAR, 2002);
        youngCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        youngCalendar.set(Calendar.DAY_OF_MONTH, 1);
        youngBirthdate = youngCalendar.getTime();

        request = RentingRequest.builder().build();
        request.setClientId(1L);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenClientIsOlderTHanEighty() {
        request.setDeadline(12);
        when(clientMapper.getBirthdate(anyLong())).thenReturn(oldBirthdate);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenClientIsYoungAndDDeadlineIsShort() {
        request.setDeadline(12);
        when(clientMapper.getBirthdate(anyLong())).thenReturn(youngBirthdate);

        Assertions.assertFalse(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenClientIsYoungAndDDeadlineTooLong() {
        request.setDeadline(720);
        when(clientMapper.getBirthdate(anyLong())).thenReturn(youngBirthdate);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenClientIsOlderTHanEightyAndDDeadlineTooLong() {
        request.setDeadline(720);
        when(clientMapper.getBirthdate(anyLong())).thenReturn(oldBirthdate);

        Assertions.assertTrue(sut.denegate(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenClientIsOldAndDeadlineIsShort() {
        request.setDeadline(6);
        when(clientMapper.getBirthdate(anyLong())).thenReturn(oldBirthdate_2);

        Assertions.assertFalse(sut.denegate(request));
    }

}
*/
