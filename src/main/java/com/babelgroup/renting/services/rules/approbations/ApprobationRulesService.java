package com.babelgroup.renting.services.rules.approbations;

import com.babelgroup.renting.entities.RentingRequest;

public interface ApprobationRulesService {

    boolean approveRules(RentingRequest rentingRequestDto);
}
