package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.entities.dtos.VehicleDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {

    @Select("SELECT ID_VEHICULO, MARCA, MODELO, VALOR, CILINDRADA, POTENCIA, CANTIDAD_ASIENTOS, CUOTA_BASE, COLOR \n" +
            "FROM INGUNIV_SCORING.VEHICULO \n" +
            "WHERE ID_VEHICULO = #{vehicleId}\n")
    Vehicle getVehicleById(@Param ("vehicleId") Long vehicleId);

    @Select("SELECT ID_VEHICULO FROM INGUNIV_SCORING.VEHICULO")
    List<Long> getVehicleIds();

    @Select("SELECT ID_VEHICULO, " +
            "MARCA, " +
            "MODELO, " +
            "VALOR, " +
            "CILINDRADA, " +
            "POTENCIA, " +
            "CANTIDAD_ASIENTOS, " +
            "CUOTA_BASE, " +
            "COLOR\n" +
            "FROM INGUNIV_SCORING.vehiculo")
    List<Vehicle> getAllVehicles();

    @Select("SELECT ID_VEHICULO, " +
            "MARCA, " +
            "MODELO, " +
            "VALOR, " +
            "CILINDRADA, " +
            "POTENCIA, " +
            "CANTIDAD_ASIENTOS, " +
            "CUOTA_BASE, " +
            "COLOR\n" +
            "FROM INGUNIV_SCORING.vehiculo\n" +
            "<script>" +
            "<where>" +
            "<if test='brand != null'> AND MARCA = #{brand} </if>" +
            "<if test='color != null'> AND COLOR = #{color} </if> " +
            "<if test='model != null'> AND MODELO = #{model} </if> " +
            "<if test='minBaseFee != null'> AND CUOTA_BASE >= #{minBaseFee} </if>" +
            "<if test='maxBaseFee != null'> AND CUOTA_BASE <= #{maxBaseFee} </if> " +
            "</where></script>")
    List<Vehicle> getVehicles(String brand, String color,
                              String model, Double minBaseFee, Double maxBaseFee);

    @Select("SELECT v.ID_VEHICULO " +
            "FROM INGUNIV_SCORING.VEHICULO v\n " +
            "WHERE v.MARCA = #{vehicleDto.brand} " +
            "AND v.MODELO = #{vehicleDto.model}")
    Long getVehicleIdByBrandAndModel(@Param("vehicleDto") VehicleDto vehicleDto);

    @Insert("insert into INGUNIV_SCORING.vehiculo (ID_VEHICULO, MARCA, MODELO, VALOR, CILINDRADA, POTENCIA, CANTIDAD_ASIENTOS, " +
            "CUOTA_BASE, COLOR) values (#{id}, #{brand}, #{model}, #{price}, #{cylinderCapacity}, #{power}, " +
            "#{numberOfSeats}, #{baseFee}, #{color})")
    Long addVehicle(Vehicle vehicle);


    @Insert("insert into INGUNIV_SCORING.extras_vehiculo (id_extra, id_vehiculo)" +
            " values (:extraId, :vehicleId)")
    int addExtraToVehicle(long vehicleId, long extraId);

    @Select("select codigo from INGUNIV_SCORING.extras where ID_EXTRA = :extraId")
    String getExtraFromId(long extraId);
}
