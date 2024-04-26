package com.babelgroup.renting.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Freelance {
    private Long employeeId;
    private Double grossIncome;
    private Double netIncome;
    private Integer yearSalary;
}
