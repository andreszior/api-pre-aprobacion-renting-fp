package com.babelgroup.renting.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Employee {
    private Long id;
    private Long clientId;
}
