package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Income;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.IncomeDTO;

import java.util.Date;
import java.util.List;

public interface IncomeService {

    Long createFreelance(Freelance freelance);

    Long createSalaried(Salaried salaried);

    Date getEmploymentYear(Long idCliente);

    long getGrossIncome(Long idCliente, int year);

    List<IncomeDTO> getIncomes (Long idCliente);

    Salaried getSalaried(Long idCliente);

    Long createIncome(IncomeDTO incomeDTO);
}
