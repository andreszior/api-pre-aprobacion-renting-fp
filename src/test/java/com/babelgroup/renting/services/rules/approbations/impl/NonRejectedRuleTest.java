package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class NonRejectedRuleTest {

    private RentingRequestMapper mapper;
    private  NonRejectedRule sut;

    @BeforeEach
    void setSut(){
        mapper = Mockito.mock(RentingRequestMapper.class);
        sut = new NonRejectedRule(mapper);
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenNoPreviousRejected(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        when(mapper.getLastRentingRequestWithWarranty(request)).thenReturn(null);

        assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnTrue_WhenPreviousRejectedIsMoreThanTwoYearsAgo(){
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -3);
        Date threeYearsAgo = cal.getTime();

        when(mapper.getLastRentingRequestWithWarranty(request)).thenReturn(threeYearsAgo);

        assertTrue(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenPreviousRejectionIsLessThanTwoYearsAgo() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date oneYearAgo = cal.getTime();

        when(mapper.getLastRentingRequestWithWarranty(request)).thenReturn(oneYearAgo);

        assertFalse(sut.approve(request));
    }

    @Test
    void testApprove_ShouldReturnFalse_WhenPreviousRejectionIsExactlyTwoYearsAgo() {
        RentingRequest request = RentingRequest.builder().build();
        request.setClientId(1L);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        Date twoYearsAgo = cal.getTime();

        when(mapper.getLastRentingRequestWithWarranty(request)).thenReturn(twoYearsAgo);

        assertTrue(sut.approve(request));
    }
}
