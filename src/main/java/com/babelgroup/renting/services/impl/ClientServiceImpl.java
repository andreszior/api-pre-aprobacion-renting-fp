package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.mappers.ProvinceMapper;
import com.babelgroup.renting.services.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {


    private final ClientMapper clientMapper;
    private final EmployeeMapper employeeMapper;
    private final CountryMapper countryMapper;

    public ClientServiceImpl(ClientMapper clientMapper, EmployeeMapper employeeMapper, CountryMapper countryMapper) {
        this.clientMapper = clientMapper;
        this.employeeMapper = employeeMapper;
        this.countryMapper = countryMapper;
    }

    @Override
    public Client createClient(Client client) {
        this.clientMapper.createClient(client);
        return client;
    }

    @Override
    public Boolean updateClient(long clientId, ClientUpdateDto clientUpdateDto) {
        Client client = clientMapper.getClientById(clientId);
        if (client == null) {
            return false;
        }
        Country country = countryMapper.getCountry(clientUpdateDto.getCountry());
        client.setCountry(country);
        Long employeeId = employeeMapper.getEmployeeByClient(clientId);
        Long salariedId = employeeMapper.getSalariedId(employeeId);
        clientUpdateDto.setSalariedId(salariedId);

        return this.clientMapper.updateClient(client) && this.employeeMapper.updateSalariedSalary(clientUpdateDto)
                && this.employeeMapper.updateSalariedValues(clientUpdateDto);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        this.clientMapper.createEmployee(employee);
        return employee;
    }

    @Override
    public Salaried createSalaried(Salaried salaried) {
        this.clientMapper.createSalaried(salaried);
        return salaried;
    }

    @Override
    public SalariedIncome createSalariedIncome(SalariedIncome salariedIncome) {
        this.clientMapper.createSalariedIncome(salariedIncome);
        return salariedIncome;
    }

    @Override
    public Freelance createFreelance(Freelance freelance) {
        this.clientMapper.createFreelance(freelance);
        return freelance;
    }

    @Override
    public Boolean deleteClient(long clientId) {
        return null;
    }

    @Override
    public Boolean clientExists(String dni) {
        String client = this.clientMapper.getClient(dni);
        return client != null;
    }
}
