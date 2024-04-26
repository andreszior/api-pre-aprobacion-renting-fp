package com.babelgroup.renting.entities;

import lombok.Data;

import java.util.Date;

@Data
public class ProductContractedPerson {
    private Long productContractedId;
    private String cif;
    private Date startDate;
    private Date endDate;
}
