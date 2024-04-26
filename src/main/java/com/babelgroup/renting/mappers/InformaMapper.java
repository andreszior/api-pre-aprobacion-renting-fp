package com.babelgroup.renting.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InformaMapper {
    @Select("SELECT COUNT(*)\n" +
            "FROM informa\n" +
            "WHERE cif = :enterpiseCif")
    int countEnterpise(String enterpiseCif);

    @Select("SELECT AVG(resultado_antes_impuestos)\n" +
            "FROM INFORMA i \n" +
            "WHERE CIF = :enterpiseCif AND ANIO_BALANCE BETWEEN EXTRACT(YEAR FROM SYSDATE) - 3 AND EXTRACT(YEAR FROM SYSDATE)")
    float getEnterpriseIncomeOverThreeYears(String enterpiseCif);
}
