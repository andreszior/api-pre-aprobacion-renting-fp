package com.babelgroup.renting.ClientService;

import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Country;
import com.babelgroup.renting.entities.Province;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.ProvinceService;
import com.babelgroup.renting.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class UpdateClientTests {


    private ClientService sut;
    private ClientMapper clientMapper;
    private CountryMapper countryMapper;

    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    private ClientUpdateDto clientDto;
    private Country country;
    private Province provinceCode;
    private CountryService countryService;
    private ProvinceService provinceService;

    private ClientServiceImpl clientServiceImpl;

    @BeforeEach()
    void setUp() {

        clientMapper = Mockito.mock(ClientMapper.class);
        countryService = Mockito.mock(CountryService.class);
        provinceService = Mockito.mock(ProvinceService.class);
        clientServiceImpl = Mockito.mock(ClientServiceImpl.class);

        sut = new ClientServiceImpl(clientMapper, countryService, provinceService);
    }

    @Test
    void testUpdateClient_shouldReturnFalse_whenClientDoesNotExist() {
        // Given
        long clientId = 1L;

        // When
        when(clientMapper.getClientById(clientId)).thenReturn(null);

        // Then
        Boolean result = clientServiceImpl.updateClient(clientId, clientDto);
        Assertions.assertFalse(result);
    }


    @Test
    void testUpdateClientES_shouldCallUpdateClient_whenCalled() {
        //Given
        this.country = new Country();
        this.country.setIso3("ESP");
        this.country.setName("España");
        this.country.setId((long) 1);

        clientDto = createClientDto("España");

        //When
        when(countryService.getCountry("España")).thenReturn(this.country);
        when(clientMapper.getClientById(anyLong())).thenReturn(createClient());
        sut.updateClient(1L, clientDto);

        //Then
        verify(clientMapper, times(1)).updateClient(this.clientArgumentCaptor.capture());

        Client client = this.clientArgumentCaptor.getValue();

        Assertions.assertEquals(this.country, client.getCountry());
    }



    @Test
    void testCreateClientPT_shouldCallUpdateClient_whenCalled() {
        //Given
        this.country = new Country();
        this.country.setIso3("POR");
        this.country.setName("Portugal");
        this.country.setId((long) 1);
        clientDto = createClientDto("Portugal");


        //When
        when(countryService.getCountry("Portugal")).thenReturn(this.country);
        when(clientMapper.getClientById(anyLong())).thenReturn(createClient());
        sut.updateClient( 2L, clientDto);

        //Then
        verify(clientMapper, times(1)).updateClient(this.clientArgumentCaptor.capture());

        Client client = this.clientArgumentCaptor.getValue();

        Assertions.assertEquals(this.country, client.getCountry());

    }

    private ClientUpdateDto createClientDto(String nameCountry) {
        ClientUpdateDto clientDto = new ClientUpdateDto();
        clientDto.setCountry(nameCountry);

        return clientDto;
    }
    private Client createClient() {
        Client client = Client.builder().build();
        return client;
    }



}
