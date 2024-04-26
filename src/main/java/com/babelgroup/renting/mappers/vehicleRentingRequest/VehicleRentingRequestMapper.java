package com.babelgroup.renting.mappers.vehicleRentingRequest;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleRentingRequestMapper {
    @Insert("INSERT INTO SOLICITUD_VEHICULO (" +
            "ID_SOLICITUD, " +
            "ID_VEHICULO) " +
            "VALUES (" +
            "#{rentingRequestId}, " +
            "#{vehicleId})")
    void createVehicleRentingRequest(long rentingRequestId, long vehicleId);
}