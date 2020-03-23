package com.testparkinglot;

import com.parkinglot.ParkingLotObservers;
import com.parkinglot.ParkingLotOwner;
import com.parkinglot.ParkingLotSystem;
import com.parkinglot.SecurityPerson;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicle;
    private ParkingLotObservers parkingLotOwner;
    private ParkingLotObservers securityPerson;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        //vehicle2 = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        try {
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle);
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
           // Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAVehicle_WantsToUnparkButCouldNotUnpark_ShouldThrowException() {
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(new Object());
        try {
            boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked();
           // Assert.assertTrue(checkIfVehicleIsUnParked);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
     //   parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.checkIfVehicleIsParked(vehicle);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        parkingLotSystem.registerObserver(securityPerson);
        Object vehicle2 =new Object();
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle2);
        try {
            parkingLotSystem.checkIfVehicleIsParked(vehicle2);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.unparkVehicle(vehicle);
            parkingLotSystem.checkIfVehicleIsUnParked();
            Assert.assertFalse(parkingLotOwner.checkIfSlotIsFull());
        } catch (ParkingLotException ex) { }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLotSystem.registerObserver(securityPerson);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.unparkVehicle(vehicle);
            parkingLotSystem.checkIfVehicleIsUnParked();
            Assert.assertFalse(securityPerson.checkIfSlotIsFull());
        } catch (ParkingLotException ex) { }
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_ShouldParkThem() {
        Object vehicle2=new Object();
        Object vehicle3=new Object();
        parkingLotSystem.setTotalSlotCapacity(5);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
            parkingLotSystem.parkVehicle(vehicle3);
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle);
            Assert.assertTrue(checkIfVehicleIsParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_MoreThanItsCapacity_ShouldThrowException() {
        parkingLotSystem.setTotalSlotCapacity(2);
        Object vehicle2=new Object();
        Object vehicle3=new Object();
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
            parkingLotSystem.parkVehicle(vehicle3);
            parkingLotSystem.checkIfVehicleIsParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL,ex.type);
        }
    }

    @Test
    public void givenAnEmptyParkingLot_SystemWillReturnListOfAvailableSlots() {
        List<Integer> expectedEmptySlotList = new ArrayList<>();
        expectedEmptySlotList.add(0);
        expectedEmptySlotList.add(1);
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLotSystem.getAvailableEmptySlots();
        Assert.assertEquals(expectedEmptySlotList, availableEmptySlotList);
    }

    @Test
    public void givenAParkingLot_PartiallyOccupied_AttendantWillParkTheVehicleInTheAvailableEmptySlot() {
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLotSystem.getAvailableEmptySlots();
        try {
            parkingLotSystem.parkVehicle(availableEmptySlotList.get(0), vehicle);
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle);
            Assert.assertTrue(checkIfVehicleIsParked);
        } catch (ParkingLotException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_IfSlotsAreFull_WillThrowException() {
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        List<Integer> availableSlots = parkingLotSystem.getAvailableEmptySlots();
        try {
            parkingLotSystem.parkVehicle(availableSlots.get(0), vehicle);
            parkingLotSystem.parkVehicle(availableSlots.get(1), vehicle1);
            parkingLotSystem.parkVehicle(availableSlots.get(2), vehicle2);
            parkingLotSystem.checkIfVehicleIsParked(vehicle2);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }
}
