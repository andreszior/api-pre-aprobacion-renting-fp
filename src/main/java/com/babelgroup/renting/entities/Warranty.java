package com.babelgroup.renting.entities;

import lombok.Data;

@Data
public class Warranty {
    private Long warrantyId;
    private String guarantorNif;
    private Double guaranteeValue;
    private String guaranteeDescription;
    private Long rentingRequestId;
    private WarrantyType warrantyType;
}
