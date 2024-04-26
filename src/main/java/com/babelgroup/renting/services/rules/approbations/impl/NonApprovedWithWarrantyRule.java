package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class NonApprovedWithWarrantyRule implements ApprobationRule {

    private final RentingRequestMapper mapper;
    @Override
    public boolean approve(RentingRequest request) {
        Date lastRequestDate =
                mapper.getLastRentingRequestWithWarranty(request);
        if (lastRequestDate == null) {
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastRequestDate);
        cal.add(Calendar.YEAR, 2);
        return !cal.getTime().after(new Date());
    }
}
