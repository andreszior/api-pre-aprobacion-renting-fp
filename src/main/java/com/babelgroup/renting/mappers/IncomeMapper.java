package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Income;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.IncomeDTO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface IncomeMapper {

    @Insert("INSERT INTO INGUNIV_SCORING.RENTA (ID_CLIENTE, ANIO_SALARIO, INGRESOS_NETOS, " +
            "CUENTA_PROPIA, ANTIGUEDAD_EMPLEO, CIF_EMPRESA) " +
            "VALUES (#{clientId}, #{salaryYear}, #{netIncome}, 0, " +
            "#{jobAntiquity}, #{cif})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_RENTA")
    void createSalaried(Salaried salaried);

    @Insert("INSERT INTO INGUNIV_SCORING.RENTA (ID_CLIENTE, ANIO_SALARIO, INGRESOS_NETOS, INGRESOS_BRUTOS, " +
            "CUENTA_PROPIA) " +
            "VALUES (#{clientId}, #{salaryYear}, #{netIncome}, #{grossIncome}, 1)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_RENTA")
    void createFreelance(Freelance freelance);

    @Select("SELECT AVG(r.INGRESOS_NETOS)\n" +
            "FROM RENTA r\n" +
            "WHERE r.ID_CLIENTE = :clientId AND r.ANIO_SALARIO >= (:year - 2) AND r.ANIO_SALARIO <= :year\n" +
            "GROUP BY r.ID_CLIENTE")
    Long getAverageSalary(Long clientId, int year);

    @Select("SELECT r.*\n" +
            "FROM renta r\n" +
            "WHERE r.ID_CLIENTE = :clientId AND r.CUENTA_PROPIA = 0")
    Salaried isSalaried(Long clientId);

    @Select("SELECT TO_CHAR(r.ANTIGUEDAD_EMPLEO, 'YYYY-MM-DD') FROM RENTA r WHERE r.id_cliente = :idCliente " +
            "AND r.CUENTA_PROPIA = 0 ORDER BY r.ANIO_SALARIO DESC FETCH FIRST 1 ROW ONLY;")
    Date getEploymentYear(Long clientId);

    @Select("SELECT AVG(r.INGRESOS_BRUTOS) AS MEDIA_INGRESOS_BRUTOS\n" +
            "FROM RENTA r\n " +
            "WHERE r.ID_CLIENTE = :clientId\n " +
            "AND r.ANIO_SALARIO BETWEEN (:year - 2) AND :year\n " +
            "GROUP BY r.ID_CLIENTE;")
    Long getGrossIncome(Long clientId, int year);

    @Select("SELECT (CASE WHEN COUNT(*) <> 0 THEN 'TRUE' ELSE 'FALSE' END)\n" +
            "FROM RENTA r WHERE r.ID_CLIENTE = :clientId AND ANIO_SALARIO >= (:year - 1);")
    boolean isValidIncome(Long clientId, int year);

    @Select("SELECT count(*) \n " +
            "FROM RENTA r \n " +
            "WHERE r.ID_CLIENTE = :clientId AND CUENTA_PROPIA = 1\n " +
            "GROUP BY r.ID_CLIENTE")
    int isFreelance(Long clienteId, int year);

    @Select("SELECT r.ID_CLIENTE, r.ANIO_SALARIO, r.INGRESOS_NETOS, r.INGRESOS_BRUTOS, r.CUENTA_PROPIA, " +
            "r.ANTIGUEDAD_EMPLEO, r.CIF_EMPRESA \n" +
            "FROM INGUNIV_SCORING.RENTA r \n" +
            "WHERE r.ID_CLIENTE = #{clientId}")
    @Results({
            @Result(property = "clientId", column = "ID_CLIENTE"),
            @Result(property = "netIncome", column = "INGRESOS_NETOS"),
            @Result(property = "grossIncome", column = "INGRESOS_BRUTOS"),
            @Result(property = "jobAntiquity", column = "ANTIGUEDAD_EMPLEO"),
            @Result(property = "isFreelance", column = "CUENTA_PROPIA"),
            @Result(property = "salaryYear", column = "ANIO_SALARIO"),
            @Result(property = "companyCif", column = "CIF_EMPRESA"),
    })
    List<IncomeDTO> getIncomes(@Param("clientId") Long clientId);
}
