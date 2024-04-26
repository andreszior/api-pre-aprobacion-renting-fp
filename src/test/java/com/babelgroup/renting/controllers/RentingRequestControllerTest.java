package com.babelgroup.renting.controllers;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.services.impl.RentingRequestServiceImpl;
import com.babelgroup.renting.validators.RentingRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class RentingRequestControllerTest {

    private RentingRequestController sut;
    private RentingRequestServiceImpl rentingRequestService;
    private RentingRequestValidator rentingRequestValidator;
    private List<RentingRequest> approvedRequestsList;
    private List<RentingRequest> deniedRequestsList;
    private List<RentingRequest> approvedWithWarrantyRequestsList;

    @BeforeEach
    void setUp() {
        this.rentingRequestValidator = mock(RentingRequestValidator.class);
        this.rentingRequestService = mock(RentingRequestServiceImpl.class);
        this.sut = new RentingRequestController(rentingRequestService,rentingRequestValidator);

        RentingRequest approvedRequest = RentingRequest.builder().build();
        approvedRequest.setResolution(RequestResult.APPROVED.getDescription());
        RentingRequest deniedRequest = RentingRequest.builder().build();
        deniedRequest.setResolution(RequestResult.DENIED.getDescription());
        RentingRequest approvedWithWarrantyRequest = RentingRequest.builder().build();
        approvedWithWarrantyRequest.setResolution(RequestResult.APPROVED_WITH_WARRANTY.getDescription());

        this.approvedRequestsList = Arrays.asList(approvedRequest, approvedRequest, approvedRequest);
        this.deniedRequestsList = Arrays.asList(deniedRequest, deniedRequest, deniedRequest);
        this.approvedWithWarrantyRequestsList = Arrays.asList(approvedWithWarrantyRequest, approvedWithWarrantyRequest, approvedWithWarrantyRequest);
    }

    @Test
    void getFilteredRentingRequests_ShouldReturnApprovedRequests_WhenStatusIsApproved() {
        List<RentingRequestDto> expected = new ArrayList<>();
        for (RentingRequest request : approvedRequestsList) {
            RentingRequestDto dto = RentingRequestDto.builder().build();
            dto.setResolution(request.getResolution());
            expected.add(dto);
        }
        when(rentingRequestService.getFilteredRentingRequests(RequestResult.APPROVED.getDescription())).thenReturn(approvedRequestsList);

        ResponseEntity<List<RentingRequestDto>> response = this.sut.getFilteredRentingRequests(RequestResult.APPROVED.name());
        assertEquals(expected, response.getBody());

        verify(rentingRequestService, times(1)).getFilteredRentingRequests(RequestResult.APPROVED.getDescription());
    }

    @Test
    void getFilteredRentingRequests_ShouldReturnDeniedRequests_WhenStatusIsDenied() {
        List<RentingRequestDto> expected = new ArrayList<>();
        for (RentingRequest request : deniedRequestsList) {
            RentingRequestDto dto = RentingRequestDto.builder().build();
            dto.setResolution(request.getResolution());
            expected.add(dto);
        }
        when(rentingRequestService.getFilteredRentingRequests(RequestResult.DENIED.getDescription())).thenReturn(deniedRequestsList);

        ResponseEntity<List<RentingRequestDto>> response = this.sut.getFilteredRentingRequests(RequestResult.DENIED.name());
        assertEquals(expected, response.getBody());

        verify(rentingRequestService, times(1)).getFilteredRentingRequests(RequestResult.DENIED.getDescription());
    }

    @Test
    void getFilteredRentingRequests_ShouldReturnApprovedWithWarrantyRequests_WhenStatusIsApprovedWithWarranty() {
        List<RentingRequestDto> expected = new ArrayList<>();
        for (RentingRequest request : approvedWithWarrantyRequestsList) {
            RentingRequestDto dto = RentingRequestDto.builder().build();
            dto.setResolution(request.getResolution());
            expected.add(dto);
        }
        when(rentingRequestService.getFilteredRentingRequests(RequestResult.APPROVED_WITH_WARRANTY.getDescription())).thenReturn(approvedWithWarrantyRequestsList);

        ResponseEntity<List<RentingRequestDto>> response = this.sut.getFilteredRentingRequests(RequestResult.APPROVED_WITH_WARRANTY.name());
        assertEquals(expected, response.getBody());

        verify(rentingRequestService, times(1)).getFilteredRentingRequests(RequestResult.APPROVED_WITH_WARRANTY.getDescription());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"INVALID"})
    void getFilteredRentingRequests_ShouldReturnBadRequest_WhenStatusIsInvalidOrEmptyOrNull(String status) {
        ResponseEntity<List<RentingRequestDto>> response = this.sut.getFilteredRentingRequests(status);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(rentingRequestService, never()).getFilteredRentingRequests(status);
    }
}