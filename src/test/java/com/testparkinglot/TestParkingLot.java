package com.testparkinglot;

import com.parkinglot.ParkingLotOwner;
import com.parkinglot.ParkingLotSystem;
import com.parkinglot.SecurityPerson;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicleType;
    private static ParkingLotOwner parkingLotOwner;
    private static SecurityPerson securityPerson;

    @BeforeClass
    public static void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicleType = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.registerLotOwner(parkingLotOwner);
        parkingLotSystem.registerSecurityPerson(securityPerson);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked();
            Assert.assertTrue(checkIfVehicleIsParked);
        } catch (ParkingLotException ex) {
        }
    }

    @Test
    public void givenInitiallyAParkedVehicle_WantsToUnPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.unparkVehicle(vehicleType);
        try {
            boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked();
            Assert.assertTrue(checkIfVehicleIsUnParked);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAVehicle_WantsToUnparkButCouldNotUnpark_ShouldThrowException() {
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.unparkVehicle(new Object());
        try {
            boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked();
            Assert.assertTrue(checkIfVehicleIsUnParked);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        parkingLotSystem.registerLotOwner(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        parkingLotSystem.registerLotOwner(parkingLotOwner);
        parkingLotSystem.registerSecurityPerson(securityPerson);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
        boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
        Assert.assertTrue(checkIfSlotIsFull);
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        parkingLotSystem.registerLotOwner(parkingLotOwner);
        parkingLotSystem.registerSecurityPerson(securityPerson);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
        boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
        Assert.assertTrue(checkIfSlotIsFull);
    }
}
