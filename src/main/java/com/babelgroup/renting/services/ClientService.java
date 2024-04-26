package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;

public interface ClientService {

    Client createClient(Client client);

    Boolean updateClient(long clientId, ClientUpdateDto client);

    Boolean deleteClient(long clientId);

    Boolean clientExists(String dni);

    Employee createEmployee(Employee employee);

    Salaried createSalaried(Salaried salaried);

    SalariedIncome createSalariedIncome(SalariedIncome salariedIncome);

    Freelance createFreelance(Freelance freelance);
}