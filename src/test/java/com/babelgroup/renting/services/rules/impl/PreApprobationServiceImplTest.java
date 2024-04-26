package com.babelgroup.renting.services.rules.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.services.rules.PreApprobationService;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import com.babelgroup.renting.services.rules.approbations.ApprobationRulesService;
import com.babelgroup.renting.services.rules.approbations.impl.DebtRule;
import com.babelgroup.renting.services.rules.denegations.DenegationRulesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PreApprobationServiceImplTest {

    private PreApprobationServiceImpl sut;
    private ApprobationRulesService approbationRulesService;
    private DenegationRulesService denegationRulesService;

    private RentingRequest rentingRequest;

    @BeforeEach
    void setUp(){
        approbationRulesService = Mockito.mock(ApprobationRulesService.class);
        denegationRulesService = Mockito.mock(DenegationRulesService.class);
        sut = new PreApprobationServiceImpl(approbationRulesService,denegationRulesService);
        rentingRequest = RentingRequest.builder().build();
    }

    @Test
    void preApprobation_shouldReturnPreApproved_whenAllApprobationRulesPassAndNoneDenegationRulesDenied(){

        when(approbationRulesService.approveRules(rentingRequest)).thenReturn(true);
        when(denegationRulesService.runDenegationRules(rentingRequest)).thenReturn(false);

        RequestResult result = sut.calculatePreResult(rentingRequest);

        assertEquals(result, RequestResult.PREAPPROVED);

    }

    @Test
    void preApprobation_shouldReturnPreDenied_whenSomeDenegationRulesDenied(){
        when(denegationRulesService.runDenegationRules(rentingRequest)).thenReturn(true);

        RequestResult result = sut.calculatePreResult(rentingRequest);

        assertEquals(result, RequestResult.PREDENIED);

    }

    @Test
    void preApprobation_shouldReturnPending_whenNoneDenegationRulesDeniedAndSomeApproobationRulesDenied(){
        when(denegationRulesService.runDenegationRules(rentingRequest)).thenReturn(false);
        when(approbationRulesService.approveRules(rentingRequest)).thenReturn(false);

        RequestResult result = sut.calculatePreResult(rentingRequest);

        assertEquals(result, RequestResult.PENDING);

    }





}