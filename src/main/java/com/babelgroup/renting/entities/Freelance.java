package com.babelgroup.renting.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Freelance {
    private Long clientId;
    private Double grossIncome;
    private Double netIncome;
    private Integer yearSalary;
}
