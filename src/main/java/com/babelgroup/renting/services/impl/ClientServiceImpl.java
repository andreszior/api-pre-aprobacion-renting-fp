package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.logger.Log;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.ProvinceService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {


    private final ClientMapper clientMapper;
    private final IncomeMapper incomeMapper;
    private final CountryMapper countryMapper;

    public ClientServiceImpl(ClientMapper clientMapper, IncomeMapper incomeMapper, CountryMapper countryMapper) {

        this.clientMapper = clientMapper;
        this.incomeMapper = incomeMapper;
        this.countryMapper = countryMapper;
        this.countryService = countryService;
        this.provinceService = provinceService;
    }

    @Override
    public Client createClient(ClientDto clientDto) {
        Client client = buildClientEntity(clientDto);
        Employee employee = buildEmployeeEntity(client);
        createEmployee(employee);

        createSalariedEntries(clientDto, employee);
        createFreelanceEntries(clientDto, employee);
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
        Long employeeId = incomeMapper.getEmployeeByClient(clientId);
        Long salariedId = incomeMapper.getSalariedId(employeeId);
        clientUpdateDto.setSalariedId(salariedId);

        return this.clientMapper.updateClient(client) && this.incomeMapper.updateSalariedSalary(clientUpdateDto)
                && this.incomeMapper.updateSalariedValues(clientUpdateDto);
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

    public Client buildClientEntity(ClientDto clientDto) {
        return Client.builder()
                .dni(clientDto.getDni())
                .name(clientDto.getName())
                .rating(0)
                .birthdate(clientDto.getBirthdate())
                .lastnameFirst(clientDto.getLastnameFirst())
                .lastnameSecond(clientDto.getLastnameSecond())
                .country(countryService.getCountry(clientDto.getCountry()))
                .provinceCode(provinceService.getProvince(clientDto.getProvinceCode()))
                .build();
    }

    public Employee buildEmployeeEntity(Client client) {
        return Employee.builder()
                .id(client.getId())
                .clientId(client.getId())
                .build();
    }

    public void createSalariedEntries(ClientDto clientDto, Employee employee) {
        if (isSalaried(clientDto)) {
            Salaried salaried = Salaried.builder()
                    .jobAntiquity(clientDto.getJobAntiquity())
                    .employeeId(employee.getId())
                    .cif(clientDto.getCompanyCif())
                    .build();

            createSalaried(salaried);
            Log.logInfo("Registro cliente asalariado creado correctamente.");

            SalariedIncome salaryIncome = SalariedIncome.builder()
                    .netIncome(clientDto.getNetIncome())
                    .salariedId(salaried.getId())
                    .salaryYear(clientDto.getSalaryYear())
                    .build();

            createSalariedIncome(salaryIncome);
            Log.logInfo("Registro ingresos asalariado creado correctamente.");
        }
    }

    public void createFreelanceEntries(ClientDto clientDto, Employee employee){
        if (isFreelance(clientDto)) {
            Freelance freelance = Freelance.builder()
                    .netIncome(clientDto.getNetIncome())
                    .grossIncome(clientDto.getGrossIncome())
                    .yearSalary(clientDto.getSalaryYear())
                    .employeeId(employee.getId())
                    .build();

            createFreelance(freelance);
            Log.logInfo("Registro cliente autónomo creado correctamente.");
        }
    }

    public Boolean isFreelance(ClientDto clientDto) {
        return clientDto.getGrossIncome() != null && clientDto.getNetIncome() != null && clientDto.getSalaryYear() != null;
    }

    public Boolean isSalaried(ClientDto clientDto) {
        return clientDto.getJobAntiquity() != null && clientDto.getCompanyCif() != null && clientDto.getNetIncome() != null && clientDto.getSalaryYear() != null;
    }
}
