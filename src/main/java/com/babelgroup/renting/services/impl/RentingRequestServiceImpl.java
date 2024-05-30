package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.entities.RequestResult;
import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.RentingRequestDto;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import com.babelgroup.renting.exceptions.EmptyRentingRequestException;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import com.babelgroup.renting.mappers.VehicleMapper;
import com.babelgroup.renting.mappers.rentingRequest.RentingRequestMapper;
import com.babelgroup.renting.services.FeeCalculationService;
import com.babelgroup.renting.services.RentingRequestService;
import com.babelgroup.renting.services.rules.PreApprobationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class RentingRequestServiceImpl implements RentingRequestService {
    private final RentingRequestMapper rentingRequestMapper;
    private final PreApprobationService preApprobationService;
    private final VehicleMapper vehicleMapper;
    private final FeeCalculationService feeCalculationService;

    public RentingRequestServiceImpl(RentingRequestMapper rentingRequestMapper, PreApprobationService preApprobationService,
                                     VehicleMapper vehicleMapper, FeeCalculationService feeCalculationService) {
        this.rentingRequestMapper = rentingRequestMapper;
        this.preApprobationService = preApprobationService;
        this.vehicleMapper = vehicleMapper;
        this.feeCalculationService = feeCalculationService;
    }

    @Override
    public RentingRequest createRentingRequest(RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {
        RentingRequest rentingRequest = convertToEntity(rentingRequestDto);
        if (rentingRequest == null) throw new EmptyRentingRequestException();
        rentingRequest.setResolution(preApprobationService.calculatePreResult(rentingRequest).toString());
        rentingRequestMapper.createRentingRequest(rentingRequest);
        return rentingRequest;
    }

    @Override
    public RentingRequest updateRentingRequestStatus(long rentingRequestId, String status) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequestMapper.updateRentingRequestStatus(rentingRequestId, status);
    }

    @Override
    public RentingRequest getRentingRequest(long rentingRequestId) throws RentingRequestNotFoundException {
        RentingRequest rentingRequest = rentingRequestMapper.findRentingRequestById(rentingRequestId);
        if (rentingRequest == null) throw new RentingRequestNotFoundException();
        return rentingRequest;
    }

    @Override
    public List<RentingRequest> getFilteredRentingRequests(String rentingRequestStatus) {
        return rentingRequestMapper.findRentingRequestsByStatus(rentingRequestStatus);
    }

    @Override
    public List<Long>getUsedIds() {
        return rentingRequestMapper.findUsedIds();
    }


    private Long convertToVehicle(VehicleDto vehicleDto){
        return vehicleMapper.getVehicleIdByBrandAndModel(vehicleDto);
    }


    private Vehicle vehicleBuild(VehicleDto vehicleDto){
        Long vehicleId = convertToVehicle(vehicleDto);
        if (vehicleId == null || vehicleId == 0) {
            Long numeroMasAlto = vehicleMapper.getVehicleIds().get(0);
            for (int i = 1; i < vehicleMapper.getVehicleIds().size(); i++) {
                Long elementoActual = vehicleMapper.getVehicleIds().get(i);
                if (elementoActual > numeroMasAlto) {
                    numeroMasAlto = elementoActual;
                }
            }
            vehicleId = numeroMasAlto + 1;
        }
        return Vehicle.builder()
                .id(vehicleId)
                .brand(vehicleDto.getBrand())
                .model(vehicleDto.getModel())
                .price(vehicleDto.getPrice())
                .cylinderCapacity(vehicleDto.getCylinderCapacity())
                .power(vehicleDto.getPower())
                .numberOfSeats(vehicleDto.getNumberOfSeats())
                .baseFee(vehicleDto.getBaseFee())
                .color(vehicleDto.getColor())
                .build();
    }

    private RentingRequest convertToEntity(RentingRequestDto rentingRequestDto) throws EmptyRentingRequestException {
        if (rentingRequestDto == null) throw new EmptyRentingRequestException();
        int numVehicles = rentingRequestDto.getVehicles().size();
        Random rd = new Random();
        List<Long> usedIds = getUsedIds();
        long id;

        do {
            id = rd.nextInt(10000) + 1;
        } while (usedIds.contains(id));
        double investment = 0;
        List<Vehicle> listaVehiculos = new ArrayList<>();
        for (VehicleDto vehiculoDto: rentingRequestDto.getVehicles()){
            Vehicle vehiculo = vehicleBuild(vehiculoDto);
            investment += vehiculo.getPrice();
            listaVehiculos.add(vehiculo);
        }

        RentingRequest rentingRequest = RentingRequest.builder()
                .id(id)
                .clientId(rentingRequestDto.getClientId())
                .rentingRequestDate(new Date())
                .effectiveDateRenting(rentingRequestDto.getEffectiveDateRenting())
                .resolutionDate(new Date())
                .numberOfVehicles(numVehicles)
                .vehicles(listaVehiculos)
                .investment(investment)
                .fee(null)
                .deadline(12)
                .resolution(RequestResult.PENDING.getDescription())
                .build();
        rentingRequest.setFee(feeCalculationService.calculateFee(rentingRequest));
        return rentingRequest;
    }

    public Boolean canDeleteRequestWithDeniedStatus(long rentingRequestId) {
        return this.rentingRequestMapper.numberOfDeniedRequest(rentingRequestId) <= 0;
    }

    private boolean isRequestAlreadyDeleted(long rentingRequestId) {
        int deletionStatus = rentingRequestMapper.getDeletionStatus(rentingRequestId);
        return  deletionStatus == 1;
    }

    public boolean deleteRentingRequest(long rentingRequestId) {
        if (isRequestAlreadyDeleted(rentingRequestId)) {
            return false;
        }
        if (canDeleteRequestWithDeniedStatus(rentingRequestId)) {
            this.rentingRequestMapper.deleteRentingRequest(rentingRequestId);
            return true;
        }
        return false;
    }
}