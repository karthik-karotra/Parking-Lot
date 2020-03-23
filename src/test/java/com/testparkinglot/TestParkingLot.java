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
    private static Object vehicle;
    private ParkingLotObservers parkingLotOwner;
    private ParkingLotObservers securityPerson;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        try {
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked();
            Assert.assertTrue(checkIfVehicleIsParked);
        } catch (ParkingLotException ex) {
        }
    }

    @Test
    public void givenInitiallyAParkedVehicle_WantsToUnPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        try {
            boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked();
            Assert.assertTrue(checkIfVehicleIsUnParked);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAVehicle_WantsToUnparkButCouldNotUnpark_ShouldThrowException() {
        parkingLotSystem.parkVehicle(vehicle);
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
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
            boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        } catch (ParkingLotException ex) { }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        parkingLotSystem.registerObserver(securityPerson);
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
            boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        } catch (ParkingLotException ex) { }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertFalse(parkingLotOwner.checkIfSlotIsFull());
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLotSystem.registerObserver(securityPerson);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertFalse(securityPerson.checkIfSlotIsFull());
        }
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_ShouldParkThem() {
        Object vehicle1=new Object();
        Object vehicle2=new Object();
        parkingLotSystem.setTotalSlotCapacity(5);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle1);
        parkingLotSystem.parkVehicle(vehicle2);
        try {
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked();
            Assert.assertTrue(checkIfVehicleIsParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_MoreThanItsCapacity_ShouldThrowException() {
        parkingLotSystem.setTotalSlotCapacity(2);
        Object vehicle1=new Object();
        Object vehicle2=new Object();
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle1);
        parkingLotSystem.parkVehicle(vehicle2);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL,ex.type);
        }

    }

}
