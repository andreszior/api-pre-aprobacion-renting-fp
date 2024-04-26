package com.babelgroup.renting.CountryService;

import com.babelgroup.renting.mappers.CountryMapper;
import com.babelgroup.renting.services.CountryService;
import com.babelgroup.renting.services.impl.CountryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CountryServiceTests {

    private CountryService sut;
    private CountryMapper countryMapper;
    @Captor
    private ArgumentCaptor<String> accountArgumentCaptor;

    @BeforeEach()
    void setUp() {
        countryMapper = Mockito.mock(CountryMapper.class);
        sut = new CountryServiceImpl(countryMapper);
    }

    @Test
    void testGetCountry_shouldHaveSameValueParametersAsPassed_whenCalled() {
        //Given
        String expectedCountry = "ES ";
        //When
        sut.getCountry(expectedCountry);
        //Then
        verify(countryMapper, times(1)).getCountry(this.accountArgumentCaptor.capture());
        String actualCountry = this.accountArgumentCaptor.getValue();

        Assertions.assertEquals(expectedCountry, actualCountry);

    }

}
