package com.babelgroup.renting.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RequestMapper {

    @Update("UPDATE solicitud SET RESULTADO = #{result} WHERE ID_SOLICITUD = #{requestId}")
    void updateResult(int requestId, String result);

}
