package com.babelgroup.renting.entities;

import lombok.Data;

@Data
public class Debt {
    private Long id;
    private Integer nif;
    private Integer yearOfDebt;
    private Double amount;
    private Double totalInitialAmount;
    private CreditorType creditorType;
}
