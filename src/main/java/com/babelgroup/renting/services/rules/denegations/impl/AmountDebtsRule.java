package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.denegations.DenegationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmountDebtsRule implements DenegationRule {

    private final ClientMapper mapper;
    @Override
    public boolean denegate(RentingRequest request) {
        try {
            double debtAmount = mapper.getAmountDebt(request.getClientId());
            return debtAmount >= request.getFee();
        } catch (Exception e) {
            Log.logError("Failed to get amount of debt for client ID " + request.getClientId(), e);
            return false;
        }
    }
}
