package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Province;
import com.babelgroup.renting.mappers.ProvinceMapper;
import com.babelgroup.renting.services.ProvinceService;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceMapper provinceMapper;

    public ProvinceServiceImpl(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    @Override
    public Province getProvince(String provinceCode) {
        return provinceMapper.getProvince(provinceCode);
    }
}
