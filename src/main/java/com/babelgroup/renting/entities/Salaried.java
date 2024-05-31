package com.babelgroup.renting.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Salaried extends Income {
    private Date jobAntiquity;
    private String cif;

}
