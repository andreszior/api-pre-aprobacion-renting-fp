package com.babelgroup.renting.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class Salaried {
    private Long clientId;
    private Integer salaryYear;
    private Double netIncome;
    private boolean isSalaried;
    private Date jobAntiquity;
    private String cif;

}
