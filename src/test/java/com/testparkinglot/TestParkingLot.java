package com.testparkinglot;

import com.parkinglot.ParkingLotObservers;
import com.parkinglot.ParkingLotOwner;
import com.parkinglot.ParkingLotSystem;
import com.parkinglot.SecurityPerson;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicleType;
    private ParkingLotObservers parkingLotOwner;
    private ParkingLotObservers securityPerson;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicleType = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
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
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
            boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        parkingLotSystem.registerObserver(securityPerson);
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
            boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.unparkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertFalse(parkingLotOwner.checkIfSlotIsFull());
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLotSystem.registerObserver(securityPerson);
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.unparkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertFalse(securityPerson.checkIfSlotIsFull());
        }
    }
}
