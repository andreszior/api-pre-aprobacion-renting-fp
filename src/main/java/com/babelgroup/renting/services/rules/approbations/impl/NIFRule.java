package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.mappers.InformaMapper;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NIFRule implements ApprobationRule {

    private static final float TAX_LIMIT = 150000;
    private final EmployeeMapper employeeMapper;
    private final InformaMapper informaMapper;

    @Override
    public boolean approve(RentingRequest request) {
        Salaried salaried = employeeMapper.isSalaried(request.getClientId());

        if (salaried == null) {
            return true;
        }

        if(existCifInEnterpise(salaried.getCif())){
            float tax = informaMapper.getEnterpriseIncomeOverThreeYears(salaried.getCif());
            return tax > TAX_LIMIT;
        }

        return false;
    }

    private boolean existCifInEnterpise(String enterpiseCif){
        return informaMapper.countEnterpise(enterpiseCif) > 0;
    }
}
