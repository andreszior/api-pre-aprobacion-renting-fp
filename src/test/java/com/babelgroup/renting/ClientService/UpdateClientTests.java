package com.babelgroup.renting.ClientService;

import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Country;
import com.babelgroup.renting.entities.Province;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class UpdateClientTests {


    private ClientService sut;
    private ClientMapper clientMapper;
    private CountryMapper countryMapper;

    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    private ClientUpdateDto clientDto;
    private Country country;
    private Province provinceCode;
    private IncomeMapper incomeMapper;

    @BeforeEach()
    void setUp() {
        clientMapper = Mockito.mock(ClientMapper.class);
        incomeMapper = Mockito.mock(IncomeMapper.class);
        countryMapper = Mockito.mock(CountryMapper.class);
        sut = new ClientServiceImpl(clientMapper, incomeMapper, countryMapper);
    }

    @Test
    void testUpdateClientES_shouldCallUpdateClient_whenCalled() {
        //Given
        this.country = new Country();
        this.country.setIso3("ES ");
        this.country.setName("ES ");
        this.country.setId((long) 1);

        clientDto = createClientDto("España");

        //When
        when(countryMapper.getCountry("España")).thenReturn(this.country);
        when(clientMapper.getClientById(anyLong())).thenReturn(any());
        when(incomeMapper.updateSalariedSalary(clientDto)).thenReturn(true);
        when(incomeMapper.updateSalariedValues(clientDto)).thenReturn(true);
        when(incomeMapper.getSalariedId(anyLong())).thenReturn((long) 1);
        when(incomeMapper.getEmployeeByClient(anyLong())).thenReturn((long) 1);
        sut.updateClient((long) 1, clientDto);

        //Then
        verify(clientMapper, times(1)).updateClient(this.clientArgumentCaptor.capture());

        Client client = this.clientArgumentCaptor.getValue();

        Assertions.assertEquals(this.country, client.getCountry());
    }


    /*Update en lugar de Create. No vemos el sentido de que pruebe pais a pais
    y no viene reflejado en el nombre del test*/
    @Test
    void testCreateClientPT_shouldCallCreateClient_whenCalled() {
        //Given
        clientDto = createClientDto("Portugal");
        this.country = new Country();
        this.country.setIso3("POR");
        this.country.setName("POR");
        this.country.setId((long) 2);

        //When
        when(countryMapper.getCountry("Portugal")).thenReturn(this.country);
        when(incomeMapper.updateSalariedSalary(clientDto)).thenReturn(true);
        when(incomeMapper.updateSalariedValues(clientDto)).thenReturn(true);
        when(incomeMapper.getSalariedId(anyLong())).thenReturn((long) 1);
        when(incomeMapper.getEmployeeByClient(anyLong())).thenReturn((long) 1);
        sut.updateClient((long) 1, clientDto);

        //Then
        verify(clientMapper, times(1)).updateClient(this.clientArgumentCaptor.capture());

        Client client = this.clientArgumentCaptor.getValue();

        Assertions.assertEquals(this.country, client.getCountry());

    }

    private ClientUpdateDto createClientDto(String nameCountry) {
        ClientUpdateDto clientDto = new ClientUpdateDto();
        clientDto.setEmployeeId((long) 1);
        clientDto.setCountry(nameCountry);
        clientDto.setNetIncome(30000.0);
        clientDto.setGrossIncome(40000.0);
        clientDto.setJobAntiquity(3);

        return clientDto;
    }

}
