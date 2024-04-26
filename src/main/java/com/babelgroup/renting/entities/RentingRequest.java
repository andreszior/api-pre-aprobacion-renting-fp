package com.babelgroup.renting.entities;

import com.babelgroup.renting.entities.dtos.VehicleDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RentingRequest {
    private Long id;
    private Long clientId;
    private Date rentingRequestDate;
    private Date effectiveDateRenting;
    private Date resolutionDate;
    private Integer numberOfVehicles;
    private List<Vehicle> vehicles;
    private Double investment;
    private Double fee;
    private Integer deadline;
    private String resolution;
}