package com.babelgroup.renting.services.rules.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.services.rules.PreApprobationService;
import com.babelgroup.renting.services.rules.approbations.ApprobationRulesService;
import com.babelgroup.renting.services.rules.denegations.DenegationRulesService;
import org.springframework.stereotype.Service;

@Service

public class PreApprobationServiceImpl implements PreApprobationService {

    private final ApprobationRulesService approbationRulesService;
    private final DenegationRulesService denegationRulesService;

    public PreApprobationServiceImpl(ApprobationRulesService approbationRulesService, DenegationRulesService denegationRulesServiceImpl) {
        this.approbationRulesService = approbationRulesService;
        this.denegationRulesService = denegationRulesServiceImpl;
    }


    public RequestResult calculatePreResult(RentingRequest rentingRequest){
        if (denegationRulesService.runDenegationRules(rentingRequest)) {
            return RequestResult.PREDENIED;
        }
        else if (approbationRulesService.approveRules(rentingRequest)) {
            return RequestResult.PREAPPROVED;
        }
        else{
            return RequestResult.PENDING;
        }
    }
}
