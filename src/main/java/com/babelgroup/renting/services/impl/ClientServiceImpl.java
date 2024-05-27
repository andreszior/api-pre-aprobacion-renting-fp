package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.mappers.ProvinceMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.ProvinceService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {


    private final ClientMapper clientMapper;
    private final EmployeeMapper employeeMapper;
    private final ProvinceService provinceService;
    private final CountryService countryService;

    public ClientServiceImpl(ClientMapper clientMapper, EmployeeMapper employeeMapper, CountryService countryService, ProvinceService provinceService) {
        this.clientMapper = clientMapper;
        this.employeeMapper = employeeMapper;
        this.countryService = countryService;
        this.provinceService = provinceService;
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

        Country country = countryService.getCountry(clientUpdateDto.getCountry());
        client.setCountry(country);
        client.setName(clientUpdateDto.getName());
        client.setLastnameFirst(clientUpdateDto.getLastnameFirst());
        client.setLastnameSecond(clientUpdateDto.getLastnameSecond());
        Province province = provinceService.getProvince(clientUpdateDto.getProvinceCode());
        client.setProvinceCode(province);

//        return this.clientMapper.updateClient(client) && this.employeeMapper.updateSalariedSalary(clientUpdateDto)
//                && this.employeeMapper.updateSalariedValues(clientUpdateDto);
        return false;
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
