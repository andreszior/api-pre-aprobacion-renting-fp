package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Province;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProvinceMapper {

    @Select("SELECT p2.COD_PROVINCIA, p2.NOMBRE FROM INGUNIV_SCORING.PROVINCIA p2 WHERE p2.COD_PROVINCIA = CAST(#{provinceCode} as CHAR(2))")
    @Results({
            @Result(property = "id", column = "COD_PROVINCIA"),
            @Result(property = "name", column = "NOMBRE")
    })
    Province getProvince(@Param("provinceCode") String provinceCode);
}
