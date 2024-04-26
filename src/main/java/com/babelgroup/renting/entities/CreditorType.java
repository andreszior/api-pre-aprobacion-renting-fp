package com.babelgroup.renting.entities;

import lombok.Data;

@Data
public class CreditorType {
    private Long creditorTypeId;
    private String categoryCode;
    private String description;
    private Double creditorValue;
}
