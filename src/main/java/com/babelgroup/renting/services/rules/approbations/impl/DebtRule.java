package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebtRule implements ApprobationRule {

    private final ClientMapper mapper;

    @Override
    public boolean approve(RentingRequest request) {
        double debt = mapper.getAmountDebt(request.getClientId());
        return debt < request.getFee();
    }
}
