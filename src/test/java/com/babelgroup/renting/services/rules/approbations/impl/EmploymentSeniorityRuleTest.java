package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.EmployeeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Year;

import static org.mockito.Mockito.when;

public class EmploymentSeniorityRuleTest {

    private EmployeeMapper mapper;
    private EmploymentSeniorityRule sut;

    private RentingRequest request;

    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(EmployeeMapper.class);
        sut = new EmploymentSeniorityRule(mapper);
        request = RentingRequest.builder().build();
        request.setClientId(1L);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenSeniorityIsOlderThanThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(Long.valueOf(2002));
        // THEN
        Assertions.assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenSeniorityIsYoungerThanThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(Long.valueOf(Year.now().getValue()));
        // THEN
        Assertions.assertFalse(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenSeniorityIsEqualsToThreeYears() {
        // GIVEN

        // WHEN
        when(mapper.getEploymentYear(request.getClientId())).thenReturn(Long.valueOf(Year.now().getValue() - 3));
        // THEN
        Assertions.assertTrue(sut.approve(request));
    }
}
