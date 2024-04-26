package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Country;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.services.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Override
    public Country getCountry(String country) {
        return countryMapper.getCountry(country);
    }

}
