package com.babelgroup.renting.services.rules.denegations;

import com.babelgroup.renting.entities.RentingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DenegationRulesServiceImpl implements DenegationRulesService {

    private final List<DenegationRule> denegationRuleList;

    public DenegationRulesServiceImpl(List<DenegationRule> denegationRuleList) {
        this.denegationRuleList = denegationRuleList;
    }


    public boolean runDenegationRules(RentingRequest rentingRequestDto){
        for(DenegationRule rule: denegationRuleList){
            if(rule.denegate(rentingRequestDto)){
                return true;
            }
        }
        return false;
    }
}
