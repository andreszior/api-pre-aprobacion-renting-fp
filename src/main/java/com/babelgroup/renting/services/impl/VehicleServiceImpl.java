package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Extra;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.exceptions.WrongParamsException;
import com.babelgroup.renting.mappers.VehicleMapper;
import com.babelgroup.renting.services.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> getVehicles(String brand, String color, String model, Double minBaseFee, Double maxBaseFee) {
        return vehicleMapper.getVehicles(brand, color, model, minBaseFee, maxBaseFee);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleMapper.getAllVehicles();
    }

    public Long addVehicle (VehicleDto vehicleDto){
        List<Long> listId = vehicleMapper.getVehicleIds();
        Long numeroMasAlto = listId.get(0);
        for (int i = 1; i < listId.size(); i++) {
            Long elementoActual = listId.get(i);
            if (elementoActual > numeroMasAlto) {
                numeroMasAlto = elementoActual;
            }
        }
        Vehicle vehicle = Vehicle.builder()
                .id(numeroMasAlto+1)
                .brand(vehicleDto.getBrand())
                .model(vehicleDto.getModel())
                .price(vehicleDto.getPrice())
                .cylinderCapacity(vehicleDto.getCylinderCapacity())
                .power(vehicleDto.getPower())
                .numberOfSeats(vehicleDto.getNumberOfSeats())
                .baseFee(vehicleDto.getBaseFee())
                .color(vehicleDto.getColor())
                .build();
        return vehicleMapper.addVehicle(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId) {
        return vehicleMapper.getVehicleById(vehicleId);
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
