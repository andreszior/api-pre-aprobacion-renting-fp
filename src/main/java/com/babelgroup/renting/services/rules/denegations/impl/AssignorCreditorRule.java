package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.denegations.DenegationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignorCreditorRule implements DenegationRule {

    private final ClientMapper mapper;

    @Override
    public boolean denegate(RentingRequest request) {
        return mapper.getAssignorOrCreditor(request.getClientId()) > 0;
    }
}
