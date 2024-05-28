package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.mappers.InformaMapper;
import com.babelgroup.renting.mappers.IncomeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NIFRuleTest {

    private NIFRule sut;
    private IncomeMapper incomeMapper;
    private InformaMapper informaMapper;

    @BeforeEach
    void setUp() {
        incomeMapper = Mockito.mock(IncomeMapper.class);
        informaMapper = Mockito.mock(InformaMapper.class);
        sut = new NIFRule(incomeMapper, informaMapper);

        Salaried salaried = Salaried.builder().build();
        salaried.setCif("CIF");

        when(incomeMapper.isSalaried(1L)).thenReturn(null);
        when(incomeMapper.isSalaried(2L)).thenReturn(salaried);
    }

    @Test
    void testApprove_shouldReturnTrue_WhenEmployeeIsNotSalaried() {
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);
        //When
        boolean result = sut.approve(request);
        //Then
        assertTrue(result);
    }

    @Test
    void testApprove_shouldReturnFalse_WhenCifNotInInforma(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(2L);
        when(informaMapper.countEnterpise(anyString())).thenReturn(0);
        //When
        boolean result = sut.approve(request);
        //Then
        assertFalse(result);
    }

    @Test
    void testApprove_shouldReturnTrue_WhenTaxIsOverLimit(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(2L);
        when(informaMapper.countEnterpise(anyString())).thenReturn(1);
        when(informaMapper.getEnterpriseIncomeOverThreeYears(anyString())).thenReturn(150001f);
        //When
        boolean result = sut.approve(request);
        //Then
        assertTrue(result);
    }

    @Test
    void testApprove_shouldReturnFalse_WhenTaxIsUnderLimit(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(2L);
        when(informaMapper.countEnterpise(anyString())).thenReturn(1);
        when(informaMapper.getEnterpriseIncomeOverThreeYears(anyString())).thenReturn(149999f);
        //When
        boolean result = sut.approve(request);
        //Then
        assertFalse(result);
    }

    @Test
    void testApprove_shouldReturnFalse_WhenTaxIsEqualLimit(){
        //Given
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(2L);
        when(informaMapper.countEnterpise(anyString())).thenReturn(1);
        when(informaMapper.getEnterpriseIncomeOverThreeYears(anyString())).thenReturn(150000f);
        //When
        boolean result = sut.approve(request);
        //Then
        assertFalse(result);
    }
}