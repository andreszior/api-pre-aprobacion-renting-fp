package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.denegations.DenegationRule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingRule implements DenegationRule {
    private final ClientMapper clientMapper;
    @Override
    public boolean denegate(RentingRequest request) {
        try {
            String rating = clientMapper.getRating(request.getClientId());
            int ratingValue = Integer.parseInt(rating);
            return ratingValue >= 6;
        } catch (Exception e) {
            Log.logError("Failed to get rating for client ID " + request.getClientId(), e);
            return false;
        }
    }
}
