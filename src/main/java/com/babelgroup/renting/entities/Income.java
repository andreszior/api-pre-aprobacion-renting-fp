package com.babelgroup.renting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Income {
    Long id;
    Long clientId;
    Double netIncome;
    //private Double grossIncome;
    //private Date jobAntiquity;
    boolean isFreelance;
    Integer salaryYear;
    //private String companyCif;
}
