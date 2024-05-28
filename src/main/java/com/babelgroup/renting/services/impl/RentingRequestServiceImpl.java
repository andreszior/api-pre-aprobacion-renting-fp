package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.services.rules.PreApprobationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentingRequestServiceImpl implements RentingRequestService {
    private final RentingRequestMapper rentingRequestMapper;
    private final PreApprobationService preApprobationService;

    public RentingRequestServiceImpl(RentingRequestMapper rentingRequestMapper, PreApprobationService preApprobationService) {
        this.rentingRequestMapper = rentingRequestMapper;
        this.preApprobationService = preApprobationService;
    }

    @Override
    public RentingRequest createRentingRequest(RentingRequest rentingRequest) throws EmptyRentingRequestException {
        if (rentingRequest == null) throw new EmptyRentingRequestException();
        rentingRequest.setResolution(preApprobationService.calculatePreResult(rentingRequest).toString());
        return rentingRequestMapper.createRentingRequest(rentingRequest);
    }

    @Override
    public RentingRequest createRentingRequestFromDto(RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {
        return createRentingRequest(convertToEntity(rentingRequestDto));
    }

    @Override
    public RentingRequest updateRentingRequestStatus(long rentingRequestId, String status) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequestMapper.updateRentingRequestStatus(rentingRequestId, status);
    }

    @Override
    public RentingRequest getRentingRequest(long rentingRequestId) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequest;
    }

    @Override
    public RentingRequestDto getRentingRequestDto(long rentingRequestId) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        RentingRequestDto dto = null;
        try {
            dto = convertToDto(rentingRequest);
        } catch (Exception e) {
            System.err.println("Error al convertir a DTO -> getRentingRequestDto: " + e.getMessage());
        }
        return dto;
    }

    @Override
    public List<RentingRequest> getFilteredRentingRequests(String rentingRequestStatus) {
        return rentingRequestMapper.findRentingRequestsByStatus(rentingRequestStatus);
    }

    @Override
    public boolean deleteRentingRequest(long rentingRequestId) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequestMapper.deleteRentingRequest(rentingRequestId) > 0;
    }

    private RentingRequestDto convertToDto(RentingRequest rentingRequest) throws EmptyRentingRequestException {
        if (rentingRequest == null) throw new EmptyRentingRequestException();
        return RentingRequestDto.builder()
                .clientId(rentingRequest.getClientId())
                .rentingRequestDate(rentingRequest.getRentingRequestDate())
                .effectiveDateRenting(rentingRequest.getEffectiveDateRenting())
                .resolutionDate(rentingRequest.getResolutionDate())
                .numberOfVehicles(rentingRequest.getNumberOfVehicles())
                .investment(rentingRequest.getInvestment())
                .fee(rentingRequest.getFee())
                .deadline(rentingRequest.getDeadline())
                .resolution(rentingRequest.getResolution())
                .build();
    }

    private RentingRequest convertToEntity(RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {
        if (rentingRequestDto == null) throw new EmptyRentingRequestException();
        return RentingRequest.builder()
                .clientId(rentingRequestDto.getClientId())
                .rentingRequestDate(rentingRequestDto.getRentingRequestDate())
                .effectiveDateRenting(rentingRequestDto.getEffectiveDateRenting())
                .resolutionDate(rentingRequestDto.getResolutionDate())
                .numberOfVehicles(rentingRequestDto.getNumberOfVehicles())
                .investment(rentingRequestDto.getInvestment())
                .fee(rentingRequestDto.getFee())
                .deadline(rentingRequestDto.getDeadline())
                .resolution(rentingRequestDto.getResolution())
                .build();
    }

    private RentingRequestDto safeConvertToDto(RentingRequest rentingRequest) {
        try {
            return convertToDto(rentingRequest);
        } catch (EmptyRentingRequestException e) {
            return null;
        }
    }

    public Boolean canDeleteRequestWithDeniedStatus(Long rentingRequestId) {
        return this.rentingRequestMapper.numberOfDeniedRequest(rentingRequestId) <= 0;
    }

    public Boolean deleteRequest(Long rentingRequestId) {
        if (canDeleteRequestWithDeniedStatus(rentingRequestId)) {
            this.rentingRequestMapper.deleteSolicitud(rentingRequestId);
            return true;
        }
        return false;
    }


    @Override
    public List<RentingRequestDto> convertToDtoList(List<RentingRequest> rentingRequests) {
        return rentingRequests.stream().map(this::safeConvertToDto).toList();
    }
}