package com.babelgroup.renting.services.impl;

import com.babelgroup.renting.entities.Vehicle;
import com.babelgroup.renting.exceptions.WrongParamsException;
import com.babelgroup.renting.mappers.VehicleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VehicleServiceImplTest {

    private VehicleMapper mapper;
    private VehicleServiceImpl sut;
    
    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(VehicleMapper.class);
        when(mapper.getVehicles("Seat", null, null, null, null)).thenReturn(List.of(createSeatVehicle()));
        when(mapper.getVehicles("Seat", "Blanco", "ST", null, null)).thenReturn(createListVehicleThreeFilters());
        when(mapper.getVehicles("Seat", "Blanco", null, null, null)).thenReturn(createListVehicle());
        sut = new VehicleServiceImpl(mapper);
    }
    
    @Test
    void testAddExtra_ShouldReturnTheExtra_WhenWeAddCorrectlyIntoExistingVehicle() throws WrongParamsException {
        long vehicleId = 1L;
        long extraId = 1L;
        when(mapper.addExtraToVehicle(vehicleId, extraId)).thenReturn(1);
        when(mapper.getExtraFromId(extraId)).thenReturn("CLR");

        String result = sut.addExtraToVehicle(vehicleId, extraId);

        assertEquals("CLR", result);
    }

    @Test
    void testAddExtra_ShouldThrowException_WhenWeAddIntoNotExistingVehicle() throws WrongParamsException {
        long vehicleId = 100L;
        long extraId = 1L;
        when(mapper.addExtraToVehicle(vehicleId, extraId)).thenReturn(0);
        when(mapper.getExtraFromId(extraId)).thenReturn(null);

        assertThrows(WrongParamsException.class, () -> {
            sut.addExtraToVehicle(vehicleId, extraId);
        });
    }

    @Test
    void testAddExtra_ShouldThrowException_WhenWeAddIntoNotExistingExtra() throws WrongParamsException {
        long vehicleId = 1L;
        long extraId = 100L;
        when(mapper.addExtraToVehicle(vehicleId, extraId)).thenReturn(0);
        when(mapper.getExtraFromId(extraId)).thenReturn(null);

        assertThrows(WrongParamsException.class, () -> {
            sut.addExtraToVehicle(vehicleId, extraId);
        });
    }

    @Test
    void testAddExtra_ShouldReturnTheExtra_WhenWeAddCorrectlyIntoExistingVehicleWithAlreadyExtras() throws WrongParamsException {
        long vehicleId = 1L;
        long extraId = 1L;
        when(mapper.addExtraToVehicle(vehicleId, extraId)).thenReturn(1);
        when(mapper.getExtraFromId(extraId)).thenReturn("CLR");

        String result = sut.addExtraToVehicle(vehicleId, extraId);

        assertEquals("CLR", result);

        long vehicleId2 = 2L;
        long extraId2 = 2L;
        when(mapper.addExtraToVehicle(vehicleId2, extraId2)).thenReturn(1);
        when(mapper.getExtraFromId(extraId2)).thenReturn("SOL");

        String result2 = sut.addExtraToVehicle(vehicleId2, extraId2);

        assertEquals("SOL", result2);
    }
    
    @Test
    void testApprove_ShouldReturnVehiclesWithOneFilter_WhenCallWithThreeFilter() {
        // GIVEN
        String brand = "Seat";
        String color = "Blanco";
        String model = "León ST";
        List<Vehicle> vehiclesExpected = List.of(createSeatVehicle());

        // WHEN
        List<Vehicle> foundVehicles = sut.getVehicles(brand,null,null,null,null);

        // THEN
        assertEquals(vehiclesExpected, foundVehicles);
    }

    @Test
    void testApprove_ShouldReturnAllVehicles_WhenCallWithThreeFiltersFilters() {
        // GIVEN
        String brand = "Seat";
        String color = "Blanco";
        String model = "ST";
        List<Vehicle> vehiclesExpected = createListVehicleThreeFilters();

        // WHEN
        List<Vehicle> foundVehicles = sut.getVehicles(brand,color,model,null,null);

        // THEN
        assertEquals(vehiclesExpected, foundVehicles);
    }

    @Test
    void testApprove_ShouldReturnAllVehiclesTwoFilters_WhenCallWithTwoFilters() {
        // GIVEN
        String brand = "Seat";
        String color = "Blanco";

        List<Vehicle> vehiclesExpected = createListVehicle();
        // WHEN
        List<Vehicle> foundVehicles = sut.getVehicles(brand,color,null,null,null);

        // THEN
        assertEquals(vehiclesExpected, foundVehicles);
    }

    private Vehicle createSeatVehicle(){
        Vehicle seat = new Vehicle();
        seat.setId(Long.valueOf("5"));
        seat.setPrice(25000D);
        seat.setNumberOfSeats(5);
        seat.setBrand("Seat");
        seat.setModel("León ST");
        seat.setBaseFee(250.45);
        seat.setCylinderCapacity(1000);
        seat.setPower(120);

        return seat;

    }

    private List<Vehicle> createListVehicle(){
        List<Vehicle> vehicleList = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(Long.valueOf("6"));
        vehicle.setPrice(25000D);
        vehicle.setNumberOfSeats(5);
        vehicle.setBrand("Seat");
        vehicle.setColor("Blanco");
        vehicle.setModel("León ST");
        vehicle.setBaseFee(250.45);
        vehicle.setCylinderCapacity(1000);
        vehicle.setPower(120);
        vehicleList.add(vehicle);

        vehicle = new Vehicle();
        vehicle.setId(Long.valueOf("7"));
        vehicle.setPrice(35000D);
        vehicle.setNumberOfSeats(5);
        vehicle.setBrand("Seat");
        vehicle.setColor("Blanco");
        vehicle.setModel("Cupra");
        vehicle.setBaseFee(550.45);
        vehicle.setCylinderCapacity(2000);
        vehicle.setPower(250);
        vehicleList.add(vehicle);

        vehicle.setId(Long.valueOf("8"));
        vehicle.setPrice(75000D);
        vehicle.setNumberOfSeats(2);
        vehicle.setBrand("Seat");
        vehicle.setColor("Blanco");
        vehicle.setModel("Leon Cabrio");
        vehicle.setBaseFee(400.45);
        vehicle.setCylinderCapacity(1500);
        vehicle.setPower(200);
        vehicleList.add(vehicle);

        return vehicleList;
    }

    private List<Vehicle> createListVehicleThreeFilters(){
        List<Vehicle> vehicleList = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(Long.valueOf("6"));
        vehicle.setPrice(25000D);
        vehicle.setNumberOfSeats(5);
        vehicle.setBrand("Seat");
        vehicle.setColor("Blanco");
        vehicle.setModel("León ST");
        vehicle.setBaseFee(250.45);
        vehicle.setCylinderCapacity(1000);
        vehicle.setPower(120);
        vehicleList.add(vehicle);

        vehicle.setId(Long.valueOf("9"));
        vehicle.setPrice(50000D);
        vehicle.setNumberOfSeats(2);
        vehicle.setBrand("Seat");
        vehicle.setColor("Blanco");
        vehicle.setModel("León ST");
        vehicle.setBaseFee(500.0);
        vehicle.setCylinderCapacity(2000);
        vehicle.setPower(500);
        vehicleList.add(vehicle);

        return vehicleList;
    }


}