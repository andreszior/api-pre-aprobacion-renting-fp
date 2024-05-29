package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.SalariedIncome;

import java.util.Date;

public interface IncomeService {


    void createFreelance(Freelance freelance);

    void createSalaried(Salaried salaried);

    Date getEmploymentYear(Long idCliente);

    long getGrossIncome(Long idCliente, int year);

    // Puede q devuelva un boolean
    Salaried getSalaried(Long idCliente);
}
