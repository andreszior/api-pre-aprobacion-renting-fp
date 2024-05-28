package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.SalariedIncome;

public interface IncomeService {


    void createFreelance(Freelance freelance);

    void createSalaried(Salaried salaried);

    long getClientSalary(Long idCliente);

    long getEmploymentYear(Long idCliente);

    long getGrossIncome(Long idCliente, int year);

    // Puede q devuelva un boolean
    Salaried getSalaried(Long idCliente);
}
