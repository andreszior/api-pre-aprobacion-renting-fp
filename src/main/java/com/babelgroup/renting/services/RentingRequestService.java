package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;

import java.util.List;

public interface RentingRequestService {
    RentingRequest createRentingRequest(RentingRequest rentingRequest) throws EmptyRentingRequestException;
    RentingRequest updateRentingRequestStatus(long rentingRequestId, String status) throws RentingRequestNotFoundException;
    RentingRequest getRentingRequest(long rentingRequestId) throws RentingRequestNotFoundException;
    List<RentingRequest> getFilteredRentingRequests(String rentingRequestStatus);
    boolean deleteRentingRequest(long rentingRequestId) throws RentingRequestNotFoundException;
}