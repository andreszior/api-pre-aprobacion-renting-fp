package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.Extra;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.exceptions.WrongParamsException;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getVehicles(String brand, String color, String model, Double minBaseFee, Double maxBaseFee);

    String addExtraToVehicle(long vehicleId, long extraId) throws WrongParamsException;
}
