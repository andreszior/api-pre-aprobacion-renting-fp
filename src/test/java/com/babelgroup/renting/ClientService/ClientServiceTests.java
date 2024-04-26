package com.babelgroup.renting.ClientService;

import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Country;
import com.babelgroup.renting.entities.Province;
import com.babelgroup.renting.mappers.ClientMapper;
import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.mappers.EmployeeMapper;
import com.babelgroup.renting.services.ClientService;
import com.babelgroup.renting.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClientServiceTests {

    private ClientService sut;
    private ClientMapper clientMapper;
    private EmployeeMapper employeeMapper;
    private CountryMapper countryMapper;
    @Captor
    private ArgumentCaptor<Client> accountArgumentCaptor;

    @BeforeEach()
    void setUp() {
        clientMapper = Mockito.mock(ClientMapper.class);
        employeeMapper = Mockito.mock(EmployeeMapper.class);
        countryMapper = Mockito.mock(CountryMapper.class);
        sut = new ClientServiceImpl(clientMapper, employeeMapper, countryMapper);
    }

    @Test
    void testCreateClient_shouldHaveSameValueParametersAsPassed_whenCalled() {
        //Given
        Client expectedClient = createClientEntity();
        //When
        sut.createClient(expectedClient);
        //Then
        verify(clientMapper, times(1)).createClient(this.accountArgumentCaptor.capture());
        Client client = this.accountArgumentCaptor.getValue();

        Assertions.assertEquals(expectedClient, client);

    }

    private Client createClientEntity() {
        return Client.builder()
                .dni("12345678A")
                .name("Juan")
                .lastnameFirst("Pérez")
                .lastnameSecond("González")
                .rating(5)
                .birthdate(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime())
                .country(setCountry("ES "))
                .provinceCode(setProvince("2"))
                .build();
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

}
