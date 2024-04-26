package com.babelgroup.renting.services.rules.approbations;

import com.babelgroup.renting.entities.RentingRequest;

public interface ApprobationRule {

    boolean approve(RentingRequest request);
}
