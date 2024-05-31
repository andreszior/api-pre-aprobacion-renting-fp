/*
package com.babelgroup.renting.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.impl.RentingRequestServiceImpl;
import com.babelgroup.renting.services.rules.PreApprobationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

//@SpringBootTest
public class RentingRequestServiceTest {
    private RentingRequestMapper mapper;
    private RentingRequestServiceImpl sut;
    private RentingRequest request;

    private PreApprobationService preApprobationService;

    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(RentingRequestMapper.class);
        preApprobationService = Mockito.mock(PreApprobationService.class);
        sut = new RentingRequestServiceImpl(mapper, preApprobationService);
        request = RentingRequest.builder().clientId(1L).build();
    }

    @Test
    void testCreateRentingRequest_ShouldReturnNotNull_thenCreateRentingRequest() {
        RentingRequest pruebaRentingRequest = createRentingRequest();

        when(mapper.createRentingRequest(pruebaRentingRequest)).thenReturn(pruebaRentingRequest);
        when(preApprobationService.calculatePreResult(eq(pruebaRentingRequest))).thenReturn(RequestResult.PENDING);
        RentingRequest resultado = null;
        try {
            resultado = sut.createRentingRequest(pruebaRentingRequest);
        } catch (EmptyRentingRequestException e) {
            fail("Se lanzó una excepción EmptyRentingRequestException cuando no debería haber sido lanzada.");
        }
        verify(mapper).createRentingRequest(pruebaRentingRequest);
        assertEquals(pruebaRentingRequest, resultado);
    }

    @Test
    void testCreateRentingRequestWithNull_ShouldReturnErrorMessage() {
        try {
            sut.createRentingRequest(null);
        } catch (EmptyRentingRequestException e) {
            assertInstanceOf(EmptyRentingRequestException.class, e);
        }
    }

    @Test
    void testUpdateRentingRequest_whenRequestExists_thenUpdateStatus() throws RentingRequestNotFoundException {
        RentingRequest pruebaRentingRequest = createRentingRequest();

        when(mapper.findRentingRequestById(1L)).thenReturn(pruebaRentingRequest);
        when(mapper.updateRentingRequestStatus(1L, "Aprobada")).thenReturn(pruebaRentingRequest);

        RentingRequest resultado = null;
        try {
            resultado = sut.updateRentingRequestStatus(1L, "Aprobada");
        } catch (RentingRequestNotFoundException e) {
            fail("Se lanzó una excepción RentingRequestNotFoundException cuando no debería haber sido lanzada.");
        }

        verify(mapper).findRentingRequestById(1L);
        verify(mapper).updateRentingRequestStatus(1L, "Aprobada");
        assertEquals(pruebaRentingRequest, resultado);
    }

    @Test
    void testGetRentingRequest_whenRequestExists_thenReturnRentingRequest() throws RentingRequestNotFoundException{
        when(mapper.findRentingRequestById(1L)).thenReturn(request);
        RentingRequest result = null;
        try {
            result = sut.getRentingRequest(1L);
        } catch (RentingRequestNotFoundException e) {
            throw new RuntimeException(e);
        }
        verify(mapper).findRentingRequestById(1L);
        assertEquals(request, result);
    }

    @Test
    public void testUpdateRentingRequestStatusWithNonExistingId() throws RentingRequestNotFoundException {

       try {
           long nonExistingId = 999L;
           when(mapper.findRentingRequestById(nonExistingId)).thenReturn(null);
           sut.updateRentingRequestStatus(nonExistingId, "Aprobada");
       } catch (RentingRequestNotFoundException e) {
           assertInstanceOf(RentingRequestNotFoundException.class, e);
       }
    }

    @ParameterizedTest
    @EnumSource(RequestResult.class)
    void getFilteredRentingRequests_WhenStatusIsValid(RequestResult status) {
        RentingRequest rentingRequest =
                RentingRequest.builder()
                        .resolution(status.getDescription())
                        .build();
        when(mapper.findRentingRequestsByStatus(status.getDescription())).thenReturn(Collections.singletonList(rentingRequest));

        List<RentingRequest> result = sut.getFilteredRentingRequests(status.getDescription());

        assertEquals(1, result.size());
        assertEquals(status.getDescription(), result.get(0).getResolution());
        verify(mapper, times(1)).findRentingRequestsByStatus(status.getDescription());
    }

    private RentingRequest createRentingRequest(){
        return RentingRequest.builder()
                .clientId(1L)
                .rentingRequestDate(null)
                .effectiveDateRenting(null)
                .resolutionDate(null)
                .numberOfVehicles(1)
                .investment(1.0)
                .fee(1.0)
                .deadline(1)
                .resolution("Pendiente a revisar")
                .build();
    }
}
*/
