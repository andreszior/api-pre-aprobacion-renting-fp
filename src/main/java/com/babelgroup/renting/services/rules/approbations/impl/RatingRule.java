package com.babelgroup.renting.services.rules.approbations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.approbations.ApprobationRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class RatingRule implements ApprobationRule {
    private final ClientMapper clientMapper;

    @Override
    public boolean approve(RentingRequest request) {
        String rating = clientMapper.getRating(request.getClientId());
        int ratingValue = Integer.parseInt(rating);
        return ratingValue < 5;
    }
}
