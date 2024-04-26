package com.babelgroup.renting.services.rules.denegations;

import com.babelgroup.renting.entities.RentingRequest;

public interface DenegationRulesService {

    boolean runDenegationRules(RentingRequest rentingRequestDto);
}
