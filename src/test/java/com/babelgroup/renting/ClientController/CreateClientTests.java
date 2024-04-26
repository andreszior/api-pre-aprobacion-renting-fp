package com.babelgroup.renting.ClientController;

import com.babelgroup.renting.controllers.ClientController;
import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Country;
import com.babelgroup.renting.entities.Province;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.ProvinceService;
import com.babelgroup.renting.validators.ClientValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class CreateClientTests {

    private ClientController sut;
    private ClientService clientService;
    private ClientValidator clientValidator;
    private CountryService countryService;
    private ProvinceService provinceService;
    @Captor
    private ArgumentCaptor<Client> accountArgumentCaptor;

    private ClientDto clientDto;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        clientService = Mockito.mock(ClientService.class);
        clientValidator = Mockito.mock(ClientValidator.class);
        countryService = Mockito.mock(CountryService.class);
        provinceService = Mockito.mock(ProvinceService.class);
        sut = new ClientController(clientService, clientValidator, countryService, provinceService);
        clientDto = createClientDto();
        bindingResult = Mockito.mock(BindingResult.class);
    }

    @Test
    void testRegisterClient_shouldCallValidate_whenCalled() {
        //Given
        //When
        sut.registerClient(clientDto, bindingResult);
        //Then
        verify(clientValidator, times(1)).validate(clientDto, bindingResult);
    }

    @Test
    void testCreateClient_shouldReturnBadRequest_whenBindingResultGetAllErrorsIsTrue() {
        //Given
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        //When
        when(bindingResult.hasErrors()).thenReturn(true);
        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);
        //Then
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testCreateClient_shouldReturnBadRequest_whenClientExistsIsTrue() {
        //Given
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>("El cliente ya existe", HttpStatus.BAD_REQUEST);
        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.clientExists(clientDto.getDni())).thenReturn(true);
        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);
        //Then
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testCreateClient_shouldReturnBadRequest_whenIsNotFreelanceAndIsNotSalaried() {
        //Given
        clientDto.setNetIncome(null);
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>("El cliente debe ser autónomo o asalariado",
                HttpStatus.BAD_REQUEST);
        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.clientExists(clientDto.getDni())).thenReturn(false);
        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);
        //Then
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testCreateClient_shouldReturnBadRequest_whenCountryIsNull() {
        //Given
        Province provinceCode = setProvince("2");
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>("El país o la provincia no existen o son erroneas",
                HttpStatus.BAD_REQUEST);
        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.clientExists(clientDto.getDni())).thenReturn(false);
        when(provinceService.getProvince("2")).thenReturn(provinceCode);
        when(countryService.getCountry("ESP")).thenReturn(null);
        Client clientEntity = sut.buildClientEntity(clientDto);

        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);

        //Then
        Assertions.assertNull(clientEntity.getCountry());
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testCreateClient_shouldReturnBadRequest_whenProvinceIsNull() {
        //Given
        Country country = setCountry("ES ");
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>("El país o la provincia no existen o son erroneas",
                HttpStatus.BAD_REQUEST);
        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.clientExists(clientDto.getDni())).thenReturn(false);
        when(provinceService.getProvince("2")).thenReturn(null);
        when(countryService.getCountry("ES ")).thenReturn(country);
        Client clientEntity = sut.buildClientEntity(clientDto);

        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);

        //Then
        Assertions.assertNull(clientEntity.getProvinceCode());
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testCreateClient_shouldReturnCreated_whenClientCreatedCorrectly() {
        //Given
        Country country = setCountry("ES ");
        Province provinceCode = setProvince("2");
        Client expectedClient = createClientEntity(country, provinceCode);
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>(HttpStatus.CREATED);

        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.clientExists(clientDto.getDni())).thenReturn(false);
        when(countryService.getCountry(eq("ES "))).thenReturn(country);
        when(provinceService.getProvince(eq("2"))).thenReturn(provinceCode);
        ResponseEntity<?> actualResponseEntity = sut.registerClient(clientDto, bindingResult);

        //Then
        verify(clientService, times(1)).createClient(this.accountArgumentCaptor.capture());
        Client client = this.accountArgumentCaptor.getValue();

        Assertions.assertEquals(expectedClient, client);
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    private Country setCountry(String name) {
        Country country = new Country();
        country.setId(1L);
        country.setName(name);
        country.setIso3("ES ");
        return country;
    }

    private Province setProvince(String id) {
        Province provinceCode = new Province();
        provinceCode.setId(id);
        provinceCode.setName("Barcelona");
        return provinceCode;
    }

    private Client createClientEntity(Country country, Province provinceCode) {
        return Client.builder()
                .dni(clientDto.getDni())
                .name(clientDto.getName())
                .lastnameFirst(clientDto.getLastnameFirst())
                .lastnameSecond(clientDto.getLastnameSecond())
                .rating(clientDto.getRating())
                .birthdate(clientDto.getBirthdate())
                .country(country)
                .provinceCode(provinceCode)
                .build();
    }

    private ClientDto createClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setDni("12345678A");
        clientDto.setName("Juan");
        clientDto.setLastnameFirst("Pérez");
        clientDto.setLastnameSecond("González");
        clientDto.setRating(0);
        clientDto.setBirthdate(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        clientDto.setCountry("ES ");
        clientDto.setProvinceCode("2");
        clientDto.setNetIncome(30000.0);
        clientDto.setGrossIncome(40000.0);
        clientDto.setJobAntiquity(new Date());
        clientDto.setSalaryYear(2021);
        clientDto.setCompanyCif("112345678L");

        return clientDto;
    }

}
