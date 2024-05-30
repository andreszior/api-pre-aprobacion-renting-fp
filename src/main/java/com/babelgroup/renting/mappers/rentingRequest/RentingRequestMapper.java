package com.babelgroup.renting.mappers.rentingRequest;

import com.babelgroup.renting.entities.RentingRequest;
import com.babelgroup.renting.exceptions.RentingRequestNotFoundException;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface RentingRequestMapper {
    @Insert("INSERT INTO SOLICITUD (" +
            "ID_CLIENTE, " +
            "FECHA_SOLICITUD, " +
            "FECHA_INICIO_VIGOR_RENTING, " +
            "FECHA_RESOLUCION, " +
            "NUMERO_VEHICULOS, " +
            "INVERSION, " +
            "CUOTA, " +
            "PLAZO, " +
            "RESULTADO) " +
            "VALUES (" +
            "#{clientId}, " +
            "#{rentingRequestDate}, " +
            "#{effectiveDateRenting}, " +
            "#{resolutionDate}, " +
            "#{numberOfVehicles}, " +
            "#{investment}, " +
            "#{fee}, " +
            "#{deadline}, " +
            "#{resolution})")
    RentingRequest createRentingRequest(RentingRequest rentingRequest);

    @Update("UPDATE SOLICITUD " +
            "SET RESULTADO = #{resolution} " +
            "WHERE ID_SOLICITUD = #{rentalRequestId}")
    RentingRequest updateRentingRequestStatus(@Param("rentingRequestId") long rentingRequestId, @Param("resolution") String resolution) throws RentingRequestNotFoundException;

    @Select("SELECT ID_SOLICITUD as id, " +
            "ID_CLIENTE as clientId, " +
            "FECHA_SOLICITUD as rentingRequestDate, " +
            "FECHA_INICIO_VIGOR_RENTING as effectiveDateRenting, " +
            "FECHA_RESOLUCION as resolutionDate, " +
            "NUMERO_VEHICULOS as numberOfVehicles, " +
            "INVERSION as investment, " +
            "CUOTA as fee, " +
            "PLAZO as deadline, " +
            "RESULTADO as resolution " +
            "FROM INGUNIV_SCORING.SOLICITUD " +
            "WHERE ID_SOLICITUD = #{rentingRequestId}")
    RentingRequest findRentingRequestById(@Param("rentingRequestId") long rentingRequestId) throws RentingRequestNotFoundException;

    @Select("SELECT * FROM SOLICITUD " +
            "WHERE RESULTADO = #{status}")
    List<RentingRequest> findRentingRequestsByStatus(String status);

    @Select("SELECT COUNT (*)" +
            "FROM SOLICITUD " +
            "WHERE RESULTADO = Denegada AND ID_CLIENTE = #{clientId};")
    int numberOfDeniedRequest(@Param ("solicitudId") Long clientId);

    @Select("SELECT BORRADO_LOGICO " +
            "FROM SOLICITUD " +
            "WHERE ID_SOLICITUD = #{solicitudId}")
    int getDeletionStatus(long solicitudId);

    @Update("UPDATE SOLICITUD " +
            "SET BORRADO_LOGICO = 1 " +
            "FECHA_BORRADO = SYSDATE " +
            "WHERE ID_SOLICITUD = #{solicitudId}")
    void deleteRentingRequest(@Param("solicitudId") long solicitudId);;

    @Select("SELECT MAX(FECHA_RESOLUCION) " +
            "FROM SOLICITUD " +
            "WHERE ID_CLIENTE = #{request.clientId} " +
            "AND RESULTADO = #{request.requestResult}")
    Date getLastRentingRequestWithWarranty(RentingRequest request);
}