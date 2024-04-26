package com.babelgroup.renting.ProvinceService;

import com.babelgroup.renting.mappers.ProvinceMapper;
import com.babelgroup.renting.services.ProvinceService;
import com.babelgroup.renting.services.impl.ProvinceServiceImpl;
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
@ExtendWith(MockitoExtension.class) // no se por que pero funciona :)
public class ProvinceServiceTests {

    private ProvinceService sut;
    private ProvinceMapper provinceMapper;
    @Captor
    private ArgumentCaptor<String> accountArgumentCaptor;

    @BeforeEach()
    void setUp() {
        provinceMapper = Mockito.mock(ProvinceMapper.class);
        sut = new ProvinceServiceImpl(provinceMapper);
    }

    @Test
    void testCreateClient_shouldHaveSameValueParametersAsPassed_whenCalled() {
        //Given
        String expectedProvinceCode = "2";
        //When
        sut.getProvince(expectedProvinceCode);
        //Then
        verify(provinceMapper, times(1)).getProvince(this.accountArgumentCaptor.capture());
        String actualProvinceCode = this.accountArgumentCaptor.getValue();

        Assertions.assertEquals(expectedProvinceCode, actualProvinceCode);

    }

}
