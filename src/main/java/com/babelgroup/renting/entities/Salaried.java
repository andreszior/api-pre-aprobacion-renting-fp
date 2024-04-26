package com.babelgroup.renting.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class Salaried {
    private Long id;
    private Long employeeId;
    private Date jobAntiquity;
    private String cif;
}
