package com.babelgroup.renting.mappers;

import com.babelgroup.renting.entities.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface ClientMapper {

    @Select("SELECT p.iso3 " +
            "FROM pais p " +
            "LEFT JOIN cliente c ON p.NACIONALIDAD = c.NACIONALIDAD " +
            "WHERE c.ID_CLIENTE = :clientId AND p.iso3 = 'ES'")
    String getNacionalidad(Long clientId);

    @Select("SELECT c.rating  " +
            "FROM cliente c " +
            "WHERE c.ID_CLIENTE = :clientId")
    String getRating(Long clientId);

    @Select("SELECT Count(*) = 0 " +
            "FROM solicitud s " +
            "INNER JOIN garantia g ON s.ID_SOLICITUD = g.ID_GARANTIA " +
            "WHERE s.resultado = 'Aprobada con garantías' AND s.ID_CLIENTE = :clientId")
    boolean hasBeenGuarantor(Long clientId);

    @Select("SELECT COUNT(id_impago) " +
            "FROM titular_impago " +
            "WHERE id_cliente = :clientId")
    int getNumberOfUnpaids(Long clientId);

    @Select("SELECT COUNT(ca.COD_CATEGORIA)  " +
            "FROM CLIENTE c " +
            "LEFT JOIN DEUDA d ON c.DNI = d.NIF  " +
            "LEFT JOIN CATEGORIA_ACREEDOR ca ON d.CATEGORIA_ACREEDOR_ID = ca.CATEGORIA_ACREEDOR_ID  " +
            "WHERE c.ID_CLIENTE = :clientId AND ca.COD_CATEGORIA IN ('RT', 'FI');")
    int getAssignorOrCreditor(Long clienteId);

    @Select("SELECT d.total_impago " +
            "FROM DEUDA " +
            "JOIN CLIENTE c ON d.NIF = c.DNI " +
            "WHERE c.id_cliente = :clientID")
    double getAmountDebt(Long clientId);

    @Select("SELECT c.fecha_nacimiento " +
            "FROM CLIENTE C " +
            "WHERE c.id_cliente=:clientId")
    Date getBirthdate(Long clientId);

    @Select("SELECT COUNT(*) = 0 " +
            "FROM Producto_Contratado_Persona pcp " +
            "LEFT JOIN Producto_Contratado pc ON pcp.producto_contratado_id = pc.producto_contratado_id " +
            "LEFT JOIN Cliente c ON TRIM(LOWER(pcp.cif)) = TRIM(LOWER(c.dni)) " +
            "WHERE c.ID_Cliente = :clientId" +
            "AND pc.fecha_baja < CURRENT_DATE()")
    boolean isNewClient(Long clientId);

    @Select("SELECT (CASE WHEN COUNT(*) <> 0 THEN 'TRUE' ELSE 'FALSE' END)" +
            "FROM Cliente c" +
            "INNER JOIN solicitud s ON c.ID_CLIENTE = s.ID_CLIENTE" +
            "INNER JOIN garantia g ON G.ID_GARANTIA = s.ID_SOLICITUD" +
            "WHERE c.ID_Cliente = :clientId" +
            "AND g.NIF_AVALISTA = c.dni" +
            "AND s.resultado = 'Aprobada con garantías'")
    boolean isGuarantor(Long clientId);


    @Select("SELECT c.ID_CLIENTE, TRUNC((SYSDATE - c.FECHA_NACIMIENTO) / 365.25, 0) " +
            "FROM CLIENTE c " +
            "WHERE c.ID_CLIENTE = :clienteId;")
    int getAgeClient(Long clienteId);

    @Insert("INSERT INTO INGUNIV_SCORING.CLIENTE (DNI, NOMBRE, APELLIDO, SEGUNDO_APELLIDO, RATING, FECHA_NACIMIENTO, NACIONALIDAD, COD_PROVINCIA) " +
            "VALUES (#{dni}, #{name}, #{lastnameFirst}, #{lastnameSecond}, #{rating}, #{birthdate}, #{country.id}, #{provinceCode.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_CLIENTE")
    void createClient(Client client);

    /*
    @Insert("INSERT INTO INGUNIV_SCORING.EMPLEADO (ID_EMPLEADO, ID_CLIENTE) " +
            "VALUES (#{id}, #{clientId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_EMPLEADO")
    void createEmployee(Employee employee);

    @Insert("INSERT INTO INGUNIV_SCORING.ASALARIADO (ID_EMPLEADO, ANTIGUEDAD_EMPLEO, CIF_EMPRESA) " +
            "VALUES (#{employeeId}, #{jobAntiquity}, #{cif})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_ASALARIADO")
    void createSalaried(Salaried salaried);

    @Insert("INSERT INTO INGUNIV_SCORING.RENTA_ASALARIADO (ID_ASALARIADO, ANIO_SALARIO, INGRESOS_NETOS) " +
            "VALUES (#{salariedId}, #{salaryYear}, #{netIncome})")
    void createSalariedIncome(SalariedIncome salariedIncome);

    @Insert("INSERT INTO INGUNIV_SCORING.AUTONOMO (ID_EMPLEADO, ANIO_SALARIO, INGRESOS_NETOS, INGRESOS_BRUTOS) " +
            "VALUES (#{employeeId}, #{yearSalary}, #{netIncome}, #{grossIncome})")
    void createFreelance(Freelance freelance);

     */

    @Select("SELECT c.DNI FROM INGUNIV_SCORING.CLIENTE c WHERE c.DNI = #{dni}")
    String getClient(@Param("dni") String dni);

    Client getClientById(long clientId);

    @Update("UPATE INGUNIV_SCORING.CLIENTE c SET c.NOMBRE = #{name}, c.APELLIDO = #{lastnameFirst}, " +
            "c.SEGUNDO_APELLIDO = #{lastnameSecond}, c.NACIONALIDAD = #{country.id}, c.CODIGO_PROVINICA = #{provinceCode} WHERE c.id_cliente = #{id}")
    boolean updateClient(Client client);
}
