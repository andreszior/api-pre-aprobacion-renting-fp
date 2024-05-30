package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.services.rules.PreApprobationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
    public List<RentingRequest> getFilteredRentingRequests(String rentingRequestStatus) {
        return rentingRequestMapper.findRentingRequestsByStatus(rentingRequestStatus);
    }

    private RentingRequest convertToEntity(RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {
        if (rentingRequestDto == null) throw new EmptyRentingRequestException();
        int numVehicles = rentingRequestDto.getVehicles().size();

        double investment = 0;
        for (VehicleDto vehiculo: rentingRequestDto.getVehicles()){
                investment += vehiculo.getPrice();
        }

        double fee = investment/12;
        return RentingRequest.builder()
                .clientId(rentingRequestDto.getClientId())
                .rentingRequestDate(new Date())
                .effectiveDateRenting(rentingRequestDto.getEffectiveDateRenting())
                .resolutionDate(new Date())
                .numberOfVehicles(numVehicles)
                .investment(investment)
                .fee(fee)
                .deadline(12)
                .resolution(RequestResult.PENDING.getDescription())
                .build();
    }

    public Boolean canDeleteRequestWithDeniedStatus(long rentingRequestId) {
        return this.rentingRequestMapper.numberOfDeniedRequest(rentingRequestId) <= 0;
    }

    private boolean isRequestAlreadyDeleted(long rentingRequestId) {
        int deletionStatus = rentingRequestMapper.getDeletionStatus(rentingRequestId);
        return  deletionStatus == 1;
    }

    public boolean deleteRentingRequest(long rentingRequestId) {
        if (isRequestAlreadyDeleted(rentingRequestId)) {
            return false;
        }
        if (canDeleteRequestWithDeniedStatus(rentingRequestId)) {
            this.rentingRequestMapper.deleteRentingRequest(rentingRequestId);
            return true;
        }
        return false;
    }
}