package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Income;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.IncomeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeMapper incomeMapper) {
        this.incomeMapper = incomeMapper;
    }

    @Override
    public Long createFreelance(Freelance freelance) {
        this.incomeMapper.createFreelance(freelance);
        return freelance.getId();
    }

    @Override
    public Long createSalaried(Salaried salaried) {
        this.incomeMapper.createSalaried(salaried);
        return salaried.getId();
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
    public List<IncomeDTO> getIncomes(Long clientId) {
        return incomeMapper.getIncomes(clientId);
    }

    @Override
    public Salaried getSalaried(Long idCliente) {
        return this.incomeMapper.isSalaried(idCliente);
    }

    public Long createIncome(IncomeDTO incomeDTO) {
        try {
            if (!incomeDTO.isFreelance()) {
                Salaried salaried = Salaried.builder()
                        .clientId(incomeDTO.getClientId())
                        .netIncome(incomeDTO.getNetIncome())
                        .salaryYear(incomeDTO.getSalaryYear())
                        .jobAntiquity(incomeDTO.getJobAntiquity())
                        .cif(incomeDTO.getCompanyCif())
                        .build();
                createSalaried(salaried);
                return salaried.getId();
            } else {
                Freelance freelance = Freelance.builder()
                        .clientId(incomeDTO.getClientId())
                        .grossIncome(incomeDTO.getGrossIncome())
                        .netIncome(incomeDTO.getNetIncome())
                        .salaryYear(incomeDTO.getSalaryYear())
                        .build();
                createFreelance(freelance);
                return freelance.getId();
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la inserción de datos
            Log.logError("Error al crear la renta: " + e.getMessage(), e);
            return null;
        }
    }

}
