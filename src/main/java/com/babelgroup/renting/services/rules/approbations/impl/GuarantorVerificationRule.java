package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GuarantorVerificationRule implements ApprobationRule {
    private final ClientMapper clientMapper;

    @Override
    public boolean approve(RentingRequest request) {
        Long clientId = request.getClientId();

        boolean isNewClient = clientMapper.isNewClient(clientId);
        boolean isGuarantor = clientMapper.isGuarantor(clientId);
        if (isNewClient){
            return true;
        }else
            return !isGuarantor;
    }
}
