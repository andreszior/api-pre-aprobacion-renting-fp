package com.babelgroup.renting.entities;

import lombok.Data;

@Data
public class Extra {

   private long extraId;
   private String extraCode;
   private String extraName;
   private String extraDescription;
   private String facturationType;
   private double value;
}
