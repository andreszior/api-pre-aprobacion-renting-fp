package com.babelgroup.renting.services.rules.denegations;

import com.babelgroup.renting.entities.RentingRequest;

public interface DenegationRule {

    boolean denegate(RentingRequest request);
}
