package com.babelgroup.renting.IncomeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import com.babelgroup.renting.mappers.IncomeMapper;
import com.babelgroup.renting.services.IncomeService;
import com.babelgroup.renting.services.impl.IncomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    private IncomeDTO incomeDto;
    IncomeService sut;
    IncomeMapper incomeMapper;


    @BeforeEach()
    void setUp() {
        incomeDto = new IncomeDTO();
        incomeMapper = Mockito.mock(IncomeMapper.class);
        sut = new IncomeServiceImpl(incomeMapper);
    }



    @Test
    void testCreateIncome_shouldReturnNull_whenIsNotFreelanceAndIsNotSalaried() {
        //Given
        incomeDto.setNetIncome(null);
        Long idIncomeExpected = null;
        //When
        Long idIncomeResult = sut.createIncome(incomeDto);

        //Then
        assertEquals(idIncomeExpected, idIncomeResult);
    }

    @Test
    public void testCreateFreelance() {
        Freelance freelance = Freelance.builder().clientId(1L).build();
        sut.createFreelance(freelance);
        verify(incomeMapper, times(1)).createFreelance(freelance);
    }

    @Test
    public void testCreateSalaried() {
        Salaried salaried = Salaried.builder().clientId(1L).build();
        sut.createSalaried(salaried);
        verify(incomeMapper, times(1)).createSalaried(salaried);
    }

    @Test
    public void testGetEmploymentYear() {
        Long idCliente = 1L;
        Date employmentYear = new Date();
        when(incomeMapper.getEploymentYear(idCliente)).thenReturn(employmentYear);
        assertEquals(employmentYear, sut.getEmploymentYear(idCliente));
    }

    @Test
    public void testGetGrossIncome() {
        Long idCliente = 1L;
        int year = 2020;
        long grossIncome = 50000L;
        when(incomeMapper.getGrossIncome(idCliente, year)).thenReturn(grossIncome);
        assertEquals(grossIncome, sut.getGrossIncome(idCliente, year));
    }

    @Test
    public void testGetSalaried() {
        Long idCliente = 1L;
        Salaried salaried = Salaried.builder().clientId(idCliente).build();
        when(incomeMapper.isSalaried(idCliente)).thenReturn(salaried);
        assertEquals(salaried, sut.getSalaried(idCliente));
    }


}
