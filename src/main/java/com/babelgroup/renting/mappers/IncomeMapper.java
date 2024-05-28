package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Freelance;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.SalariedIncome;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IncomeMapper {

    @Insert("INSERT INTO INGUNIV_SCORING.RENTA (ID_CLIENTE, ANIO_SALARIO, INGRESOS_NETOS, " +
            "CUENTA_PROPIA, ANTIGUEDAD_EMPLEO, CIF_EMPRESA) " +
            "VALUES (#{clientId}, #{salaried.salaryYear}, #{salaried.netIncome}, 0, " +
            "#{salaried.jobAntiquity}, #{salaried.cif})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_CLIENTE")
    void createSalaried(Salaried salaried);

    @Insert("INSERT INTO INGUNIV_SCORING.RENTA ID_CLIENTE, ANIO_SALARIO, INGRESOS_NETOS, INGRESOS_BRUTOS" +
            "CUENTA_PROPIA) " +
            "VALUES (#{clientId}, #{freelance.yearSalary}, #{freelance.netIncome}, #{freelance.grossIncome}, 1")
    void createFreelance(Freelance freelance);

    @Select("SELECT AVG(r.INGRESOS_NETOS)\n" +
            "FROM RENTA r\n" +
            "WHERE r.ID_CLIENTE = :clientId AND r.ANIO_SALARIO >= (:year - 3) AND r.ANIO_SALARIO <= :year\n" +
            "GROUP BY r.ID_CLIENTE")
    Long getAverageSalary(Long clientId, int year);


    @Select("SELECT a.*\n" +
            "FROM asalariado a\n" +
            "LEFT JOIN empleado e ON a.ID_CLIENTE  = e.ID_CLIENTE\n" +
            "LEFT JOIN cliente c ON e.ID_CLIENTE  = c.ID_CLIENTE\n" +
            "WHERE c.ID_CLIENTE = :clientId")
    Salaried isSalaried(Long clientId);

    @Select("SELECT MIN(asa.ANIO_SALARIO)  FROM Empleado e\n" +
            "LEFT JOIN ASALARIADO asa ON e.ID_EMPLEADO = asa.ID_EMPLEADO\n" +
            "WHERE e.id_cliente = :clientID")
    long getEploymentYear(Long clientId);

    @Select("SELECT AVG(a.INGRESOS_BRUTOS) AS MEDIA_INGRESOS_BRUTOS\n" +
            "FROM cliente c\n" +
            "LEFT JOIN empleado e ON c.ID_CLIENTE = e.id_cliente\n" +
            "LEFT JOIN autonomo a ON e.id_empleado = a.id_empleado \n" +
            "WHERE c.ID_CLIENTE = :clientId\n" +
            "  AND a.ANIO_SALARIO BETWEEN (:year - 2) AND :year\n" +
            "GROUP BY c.ID_CLIENTE;")
    Long getGrossIncome(Long clientId, int year);

    @Select("SELECT count(*) \n" +
            "            FROM cliente c \n" +
            "            LEFT JOIN empleado e ON c.ID_CLIENTE  = e.id_cliente \n" +
            "            LEFT JOIN autonomo a ON e.id_empleado = a.id_empleado \n" +
            "            WHERE c.ID_CLIENTE = :clientId AND a.ANIO_SALARIO >= (:year - 3) AND a.ANIO_SALARIO <= :year\n" +
            "            GROUP BY a.ID_EMPLEADO")
    int isFreelance(Long clienteId, int year);

    @Select("SELECT e.ID_EMPLEADO  FROM EMPLEADO e\n" +
            "LEFT JOIN CLIENTE c ON e.ID_CLIENTE = #{clientId}")
    Long getEmployeeByClient(long clientId);

    @Select("SELECT a.id_asalariado FROM ASALARIADO a\n" +
            "LEFT JOIN EMPLEADO e ON e.id_asalariado = a.id_asalariado\n" +
            "WHERE e.id_empleado = #{employeeId}")
    Long getSalariedId(Long employeeId);

    @Update("UPDATE ASALARIADO a SET a.anio_salario=#{jobAntiquity}, a.cif_empresa=#{employerCIF}, a.antiguedad_empleo=#{jobAntiquity} WHERE a.id_asalariado=#{salariedId}")
    boolean updateSalariedValues(ClientUpdateDto clientUpdateDto);

    @Update("UPDATE RENTA_ASALARIADO ra SET ra.ingresos_netos=#{netIncome} WHERE ra.id_asalariado=#{salariedId}")
    boolean updateSalariedSalary(ClientUpdateDto clientUpdateDto);
}
