package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.services.rules.PreApprobationService;
import com.babelgroup.renting.services.rules.impl.PreApprobationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentingRequestServiceImpl implements RentingRequestService {
    private final RentingRequestMapper rentingRequestMapper;
    private final PreApprobationService preApprobationService;
    public RentingRequestServiceImpl(RentingRequestMapper rentingRequestMapper, PreApprobationService preApprobationService){
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
    public RentingRequest updateRentingRequestStatus(long rentingRequestId, String status) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequestMapper.updateRentingRequestStatus(rentingRequestId, status);
    }

    @Override
    public RentingRequest getRentingRequest(long rentingRequestId)  throws RentingRequestNotFoundException  {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequest;
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
}