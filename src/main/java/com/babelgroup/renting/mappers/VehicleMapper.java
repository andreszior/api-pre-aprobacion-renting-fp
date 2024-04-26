package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Client;
import com.babelgroup.renting.entities.Extra;
import com.babelgroup.renting.entities.Vehicle;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {



    @Select("SELECT v.ID_VEHICULO, " +
            "v.MARCA, " +
            "v.MODELO, " +
            "v.VALOR, " +
            "v.CILINDRADA, " +
            "v.POTENCIA, " +
            "v.CANTIDAD_ASIENTOS, " +
            "v.CUOTA_BASE, " +
            "v.COLOR\n" +
            "FROM vehiculo v\n" +
            "WHERE v.id_vehiculo = :idVehicle")
    Vehicle getVehicleById(Long idVehicle);


    @Select("SELECT ID_VEHICULO, " +
            "MARCA, " +
            "MODELO, " +
            "VALOR, " +
            "CILINDRADA, " +
            "POTENCIA, " +
            "CANTIDAD_ASIENTOS, " +
            "CUOTA_BASE, " +
            "COLOR\n" +
            "FROM vehiculo\n"   +
         "<where>" +
            " 1=1 " +
            "<if test='brand != null'> AND MARCA = #{brand} </if>" +
            "<if test='color != null'> AND COLOR = #{color} </if> " +
            "<if test='model != null'> AND MODELO = #{model} </if> " +
            "<if test='minBaseFee != null'> AND CUOTA_BASE >= #{minBaseFee} </if>" +
            "<if test='maxBaseFee != null'> AND CUOTA_BASE <= #{maxBaseFee} </if> " +
            "</where></script>")
    List<Vehicle> getVehicles(String brand, String color,
                              String model, Double minBaseFee, Double maxBaseFee);




    @Insert("insert into extras_vehiculo (id_extra, id_vehiculo)" +
            " values (:extraId, :vehicleId)")
    int addExtraToVehicle(long vehicleId, long extraId);

    @Select("select codigo from extras where ID_EXTRA = :extraId")
    String getExtraFromId(long extraId);
}
