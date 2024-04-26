package com.babelgroup.renting.services.rules.denegations.impl;

import com.babelgroup.renting.entities.RentingRequest;
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
        Date birthdate = mapper.getBirthdate(request.getClientId());
        int deadlineYears = (int) (request.getDeadline() / 12);
        int totalTime = getAge(birthdate) + deadlineYears;
        return totalTime >= 80;
    }

    private int getAge(Date date) {
        Calendar client = Calendar.getInstance();
        client.setTime(date);
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        int diff = current.get(YEAR) - client.get(YEAR);
        if (client.get(MONTH) > current.get(MONTH) ||
                (client.get(MONTH) == current.get(MONTH) && client.get(DATE) > current.get(DATE))) {
            diff--;
        }
        return diff;
    }
}
