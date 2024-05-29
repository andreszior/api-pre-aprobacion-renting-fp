package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.denegations.DenegationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnderageClientRule implements DenegationRule {

    private final ClientMapper mapper;

    @Override
    public boolean denegate(RentingRequest request) {
        try {
            int age = mapper.getAgeClient(request.getClientId());
            return age <= 17;
        } catch (Exception e) {
            Log.logError("Failed to get client age for client ID " + request.getClientId(), e);
            return false;
        }
    }
}
