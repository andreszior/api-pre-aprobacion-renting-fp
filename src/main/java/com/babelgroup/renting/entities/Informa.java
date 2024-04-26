package com.babelgroup.renting.entities;

import lombok.Data;

@Data
public class Informa {
    private Long id;
    private String cif;
    private String businessName;
    private String municipality;
    private int yearBalance;
    private Double salesValue;
    private Double resultsBeforeTaxes;
    private String provinceCode;
}
