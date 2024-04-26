package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NacionalityRule implements ApprobationRule {

    private final ClientMapper mapper;

    @Override
    public boolean approve(RentingRequest request) {
        return mapper.getNacionalidad(request.getClientId()).equals("ES");
    }
}
