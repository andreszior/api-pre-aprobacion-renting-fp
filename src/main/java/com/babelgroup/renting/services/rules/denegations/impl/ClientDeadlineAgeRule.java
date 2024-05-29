package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.rules.denegations.DenegationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

@Service
@RequiredArgsConstructor
public class ClientDeadlineAgeRule implements DenegationRule {

    private final ClientMapper mapper;

    @Override
    public boolean denegate(RentingRequest request) {
        try {
            int clientAge = mapper.getAgeClient(request.getClientId());
            int deadlineYears = (int) (request.getDeadline() / 12);
            int totalTime = clientAge + deadlineYears;
            return totalTime >= 80;
        } catch (Exception e) {
            Log.logError("Failed to get client age for client ID " + request.getClientId(), e);
            return false;
        }
    }
}
