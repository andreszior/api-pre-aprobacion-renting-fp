package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.IncomeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeMapper incomeMapper) {
        this.incomeMapper = incomeMapper;
    }

    @Override
    public void createFreelance(Freelance freelance) {
        this.incomeMapper.createFreelance(freelance);
    }

    @Override
    public void createSalaried(Salaried salaried) {
        this.incomeMapper.createSalaried(salaried);
    }

    @Override
    public Date getEmploymentYear(Long idCliente) {
        return this.incomeMapper.getEploymentYear(idCliente);
    }

    @Override
    public long getGrossIncome(Long idCliente, int year) {
        return this.incomeMapper.getGrossIncome(idCliente, year);
    }

    @Override
    // Puede q devuelva un boolean
    public Salaried getSalaried(Long idCliente) {
        return this.incomeMapper.isSalaried(idCliente);
    }


}
