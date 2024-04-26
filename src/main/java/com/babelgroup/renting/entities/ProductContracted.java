package com.babelgroup.renting.entities;

import lombok.Data;

import java.util.Date;

@Data
public class ProductContracted {
    private Long productContractedId;
    private Long productId;
    private String alias;
    private Double nominalAmount;
    private Date startDate;
    private Date endDate;
}
