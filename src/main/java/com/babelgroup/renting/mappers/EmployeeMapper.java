package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.Employee;
import com.babelgroup.renting.entities.Salaried;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {


    @Select("SELECT AVG(a.ingresos_netos) + AVG(ra.INGRESOS_NETO)\n" +
            "FROM cliente c\n" +
            "LEFT JOIN empleado e ON c.ID_CLIENTE  = e.ID_CLIENTE\n" +
            "LEFT JOIN autonomo a ON e.ID_EMPLEADO = a.ID_EMPLEADO\n" +
            "LEFT JOIN ASALARIADO asa ON e.ID_EMPLEADO = asa.ID_EMPLEADO \n" +
            "LEFT JOIN renta_asalariado ra ON asa.ID_ASALARIADO = ra.ID_ASALARIADO \n" +
            "WHERE c.ID_CLIENTE = :clientId AND a.ANIO_SALARIO >= (:year - 3) AND a.ANIO_SALARIO <= :year AND ra.ANIO_SALARIO >= (:year - 3) AND ra.ANIO_SALARIO <= :year\n" +
            "GROUP BY a.ID_EMPLEADO")
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
