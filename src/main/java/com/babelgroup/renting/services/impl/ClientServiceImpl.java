package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.IncomeService;
import com.babelgroup.renting.services.ProvinceService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {


    private final ClientMapper clientMapper;
    private final ProvinceService provinceService;
    private final IncomeService incomeService;
    private final CountryService countryService;

    public ClientServiceImpl(ClientMapper clientMapper, IncomeService incomeService, CountryService countryService, ProvinceService provinceService) {

        this.clientMapper = clientMapper;
        this.incomeService = incomeService;
        this.countryService = countryService;
        this.provinceService = provinceService;
    }

    @Override
    public Client createClient(ClientDto clientDto) {
        Client client = buildClientEntity(clientDto);
        this.clientMapper.createClient(client);
    /*
        if (isFreelance(clientDto)) {
            Freelance freelance = Freelance.builder()
                    .clientId(client.getId())
                    .grossIncome(clientDto.getGrossIncome())
                    .netIncome(clientDto.getNetIncome())
                    .yearSalary(clientDto.getSalaryYear())
                    .build();
            incomeService.createFreelance(freelance);

        } else if (isSalaried(clientDto)) {
            Salaried salaried = Salaried.builder()
                    .clientId(client.getId())
                    .jobAntiquity(clientDto.getJobAntiquity())
                    .cif(clientDto.getCompanyCif())
                    .build();
            incomeService.createSalaried(salaried);
        }

     */
       /* Employee employee = buildEmployeeEntity(client);
        createEmployee(employee);

        createSalariedEntries(clientDto, employee);
        createFreelanceEntries(clientDto, employee);*/
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

        return this.clientMapper.updateClient(client);
    }

    @Override
    public Boolean deleteClient(long clientId) {
        try{
            this.clientMapper.deleteClient(clientId);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public Boolean canDeleteUserWithNoRequests(Long clientId) {
        return this.clientMapper.getNumberOfExistingRequest(clientId) <= 0;
    }

    private boolean isClientAlreadyDeleted(long clientId) {
        int deletionStatus = clientMapper.getDeletionStatus(clientId);
        return deletionStatus == 1;
    }

    public Boolean deleteClient(long clientId) {
        if (isClientAlreadyDeleted(clientId)) {
            return false;
        }
        if (canDeleteClientWithNoRequests(clientId)) {
            this.clientMapper.deleteClient(clientId);
            return true;
        }
        return false;
    }
}
