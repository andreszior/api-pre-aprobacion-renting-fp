package com.babelgroup.renting.services.rules.approbations;

import com.babelgroup.renting.entities.RentingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ApprobationRulesServiceImpl implements ApprobationRulesService {

    private final List<ApprobationRule> approbationRuleList;

    public ApprobationRulesServiceImpl(List<ApprobationRule> approbationRuleList) {
        this.approbationRuleList = approbationRuleList;
    }


    public boolean approveRules(RentingRequest rentingRequestDto){
        for(ApprobationRule rule: approbationRuleList){
            if(!rule.approve(rentingRequestDto)){
                return false;
            }
        }
        return true;
    }
}
