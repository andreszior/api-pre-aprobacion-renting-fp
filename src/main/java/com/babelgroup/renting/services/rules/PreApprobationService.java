package com.babelgroup.renting.services.rules;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;

public interface PreApprobationService {

    RequestResult calculatePreResult(RentingRequest rentingRequest);
}
