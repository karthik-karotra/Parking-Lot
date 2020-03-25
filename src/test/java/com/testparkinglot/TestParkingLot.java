package com.testparkinglot;

import com.parkinglot.*;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicle, vehicle1;
    private ParkingLotObservers parkingLotOwner;
    private ParkingLotObservers securityPerson;
    private LotManagementSystem lotManagementSystem;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        vehicle1 = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        parkingLotSystem.initializingSlots();
        lotManagementSystem = new LotManagementSystem(2, 2);
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenAVehicle_WantsToParkButCouldNotPark_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, ex.type);
        }
    }

    @Test
    public void givenInitiallyAParkedVehicle_WantsToUnPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsUnParked);
    }

    @Test
    public void givenAVehicle_WantsToUnparkButCouldNotUnpark_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.unparkVehicle(vehicle1);
            boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLotSystem.setTotalSlotCapacity(1);
            parkingLotSystem.initializingSlots();
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle1);
            parkingLotSystem.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        try {
            parkingLotSystem.registerObserver(parkingLotOwner);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle1);
            parkingLotSystem.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        try {
            parkingLotSystem.registerObserver(securityPerson);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle1);
            parkingLotSystem.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        parkingLotSystem.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(parkingLotOwner.checkIfSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLotSystem.registerObserver(securityPerson);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.unparkVehicle(vehicle);
        parkingLotSystem.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(securityPerson.checkIfSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_MoreThanItsCapacity_ShouldThrowException() {
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
            parkingLotSystem.parkVehicle(vehicle3);
            parkingLotSystem.checkIfVehicleIsParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAnEmptyParkingLot_WillReturnListOfAvailableSlots() {
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
        parkingLotSystem.parkVehicle(availableEmptySlotList.get(0), vehicle);
        boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenAParkingLot_DriverWantsToFindHisVehicle_IfFound_ShouldReturnCorrectParkedSlot() {
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLotSystem.getAvailableEmptySlots();
        parkingLotSystem.parkVehicle(availableEmptySlotList.get(0), vehicle);
        int slotPositionOfParkedVehicle = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0, slotPositionOfParkedVehicle);
    }

    @Test
    public void givenAParkingLot_DriverWantToFindHisVehicle_IfNotFound_ShouldThrowException() {
        parkingLotSystem.setTotalSlotCapacity(2);
        parkingLotSystem.initializingSlots();
        try {
            parkingLotSystem.getAvailableEmptySlots();
            parkingLotSystem.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenAParkingLot_OwnerWantsToKnowWhenAVehicleWasParked_ShouldRetuenTime() {
        parkingLotSystem.parkVehicle(vehicle);
        LocalDateTime parkingTime = parkingLotSystem.getParkingTimeOfVehicle(vehicle);
        Assert.assertEquals(LocalDateTime.now().getMinute(), parkingTime.getMinute());
    }

    @Test
    public void givenMultipleParkingLots_WhenWantToPark_ShouldParkVehicleAnd_ReturnLotNumber() {
        lotManagementSystem.parkVehicle(vehicle);
        lotManagementSystem.parkVehicle(vehicle1);
        ParkingLotSystem assignedLot = lotManagementSystem.getLotOfParkedVehicle(vehicle1);
        Assert.assertEquals(lotManagementSystem.parkingLot.get(1), assignedLot);
    }

    @Test
    public void givenMultipleParkingLots_WhenWantToPark_ButCouldNotPark_ShouldReturnNull() {
        lotManagementSystem.parkVehicle(vehicle);
        // lotManagementSystem.parkVehicle(vehicle1);
        ParkingLotSystem assignedLot = lotManagementSystem.getLotOfParkedVehicle(vehicle1);
        Assert.assertEquals(null, assignedLot);
    }
}
