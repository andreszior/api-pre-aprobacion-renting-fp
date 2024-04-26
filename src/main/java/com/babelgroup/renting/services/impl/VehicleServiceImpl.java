package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Extra;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.exceptions.WrongParamsException;
import com.babelgroup.renting.mappers.VehicleMapper;
import com.babelgroup.renting.services.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> getVehicles(String brand, String color, String model, Double minBaseFee, Double maxBaseFee) {
        return vehicleMapper.getVehicles(brand,color,model,minBaseFee, maxBaseFee);
    }

    @Override
    public String addExtraToVehicle(long vehicleId, long extraId) throws WrongParamsException {
        int rowsAffected = vehicleMapper.addExtraToVehicle(vehicleId, extraId);

        if (rowsAffected > 0) {
            String extraCode = vehicleMapper.getExtraFromId(extraId);
            if (extraCode != null) {
                return extraCode;
            }
        }

        throw new WrongParamsException("No se ha podido añadir el extra correctamente");
    }

}
