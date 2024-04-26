package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Country;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CountryMapper {
    @Select("SELECT p.NACIONALIDAD, p.NOMBRE, p.ISO3 FROM INGUNIV_SCORING.PAIS p WHERE p.ISO3 = #{country}")
    @Results({
            @Result(property = "id", column = "NACIONALIDAD"),
            @Result(property = "name", column = "NOMBRE"),
            @Result(property = "iso3", column = "ISO3")
    })
    Country getCountry(@Param("country") String country);
}
