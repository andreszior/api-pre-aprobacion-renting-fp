
package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.IncomeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

public class EmploymentSeniorityRuleTest {

    private IncomeMapper mapper;
    private EmploymentSeniorityRule sut;

    private RentingRequest request;

    private Date date1;
    private Date date2;
    private Date date3;


    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(IncomeMapper.class);
        sut = new EmploymentSeniorityRule(mapper);
        request = RentingRequest.builder().build();
        request.setClientId(1L);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        date1 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2022);
        date2 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        date3 = calendar.getTime();
    }
    @Test
    void testApprove_ShouldReturnTrue_WhenSeniorityIsOlderThanThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(date1);
        // THEN
        Assertions.assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenSeniorityIsYoungerThanThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(date2);
        // THEN
        Assertions.assertFalse(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenSeniorityIsEqualsToThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(date3);
        // THEN
        Assertions.assertTrue(sut.approve(request));
    }
}
