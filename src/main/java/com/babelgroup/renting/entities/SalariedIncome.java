package com.babelgroup.renting.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SalariedIncome {
    private Long salariedId;
    private Double netIncome;
    private Integer salaryYear;
}
