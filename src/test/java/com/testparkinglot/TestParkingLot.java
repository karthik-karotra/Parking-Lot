package com.testparkinglot;

import com.parkinglot.*;
import com.parkinglotexception.ParkingLotException.ExceptionType;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestParkingLot {
    private static ParkingLot parkingLot;
    private static Vehicle vehicle, vehicle1, vehicle2;
    private ParkingLotObservers parkingLotOwner;
    private ParkingLotObservers securityPerson;
    private ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() {

        parkingLot = new ParkingLot(1);
        vehicle = new Vehicle("White", "Scorpio", "MH 43 AR 6451");
        vehicle1 = new Vehicle("White", "Toyota", "MH 43 AV 7854");
        vehicle2 = new Vehicle("Blue", "Toyota", "MH 43 AR 6210");
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        parkingLotSystem = new ParkingLotSystem(2, 2);
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        new ParkingLot(1).parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        boolean checkIfVehicleIsParked = parkingLot.checkIfVehicleIsParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenAVehicle_WantsToParkButCouldNotPark_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            boolean checkIfVehicleIsParked = parkingLot.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, ex.type);
        }
    }

    @Test
    public void givenInitiallyAParkedVehicle_WantsToUnPark_ShouldReturnTrue() {
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.unparkVehicle(vehicle);
        boolean checkIfVehicleIsUnParked = parkingLot.checkIfVehicleIsUnParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsUnParked);
    }

    @Test
    public void givenAVehicle_WantsToUnparkButCouldNotUnpark_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.unparkVehicle(vehicle1);
            boolean checkIfVehicleIsUnParked = parkingLot.checkIfVehicleIsUnParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLot.setTotalSlotCapacity(1);
            parkingLot.initializingSlots();
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLot.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheOwner() {
        try {
            parkingLot.registerObserver(parkingLotOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLot.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = parkingLotOwner.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldInformTheSecurityPersonal() {
        try {
            parkingLot.registerObserver(securityPerson);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLot.checkIfVehicleIsParked(vehicle1);
        } catch (ParkingLotException ex) {
            boolean checkIfSlotIsFull = securityPerson.checkIfSlotIsFull();
            Assert.assertTrue(checkIfSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLot.registerObserver(parkingLotOwner);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.unparkVehicle(vehicle);
        parkingLot.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(parkingLotOwner.checkIfSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLot.registerObserver(securityPerson);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.unparkVehicle(vehicle);
        parkingLot.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(securityPerson.checkIfSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_MoreThanItsCapacity_ShouldThrowException() {
        parkingLot.setTotalSlotCapacity(2);
        parkingLot.initializingSlots();
        Vehicle vehicle3 = new Vehicle("White");
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);
            parkingLot.checkIfVehicleIsParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
    }

    @Test
    public void givenAnEmptyParkingLot_WillReturnListOfAvailableSlots() {
        List<Integer> expectedEmptySlotList = new ArrayList<>();
        expectedEmptySlotList.add(0);
        expectedEmptySlotList.add(1);
        parkingLot.setTotalSlotCapacity(2);
        parkingLot.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLot.getAvailableEmptySlots();
        Assert.assertEquals(expectedEmptySlotList, availableEmptySlotList);
    }

    @Test
    public void givenAParkingLot_PartiallyOccupied_AttendantWillParkTheVehicleInTheAvailableEmptySlot() {
        parkingLot.setTotalSlotCapacity(2);
        parkingLot.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLot.getAvailableEmptySlots();
        parkingLot.parkVehicle(availableEmptySlotList.get(0), vehicle);
        boolean checkIfVehicleIsParked = parkingLot.checkIfVehicleIsParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenAParkingLot_DriverWantsToFindHisVehicle_IfFound_ShouldReturnCorrectParkedSlot() {
        parkingLot.setTotalSlotCapacity(2);
        parkingLot.initializingSlots();
        List<Integer> availableEmptySlotList = parkingLot.getAvailableEmptySlots();
        parkingLot.parkVehicle(availableEmptySlotList.get(0), vehicle);
        int slotPositionOfParkedVehicle = parkingLot.findVehicle(vehicle);
        Assert.assertEquals(0, slotPositionOfParkedVehicle);
    }

    @Test
    public void givenAParkingLot_DriverWantToFindHisVehicle_IfNotFound_ShouldThrowException() {
        parkingLot.setTotalSlotCapacity(2);
        parkingLot.initializingSlots();
        try {
            parkingLot.getAvailableEmptySlots();
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenAParkingLot_OwnerWantsToKnowWhenAVehicleWasParked_ShouldReturnTime() {
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        LocalDateTime parkingTime = parkingLot.getParkingTimeOfVehicle(vehicle);
        Assert.assertEquals(LocalDateTime.now().getMinute(), parkingTime.getMinute());
    }

    @Test
    public void givenMultipleParkingLots_WhenWantToPark_ShouldParkVehicleAnd_ReturnLotNumber() {
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        ParkingLot assignedLot = parkingLotSystem.getLotOfParkedVehicle(vehicle2);
        Assert.assertEquals(parkingLotSystem.parkingLot.get(1), assignedLot);
    }

    @Test
    public void givenMultipleParkingLots_WhenWantToPark_ButCouldNotPark_ShouldReturnNull() {
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle);
        ParkingLot assignedLot = parkingLotSystem.getLotOfParkedVehicle(vehicle1);
        Assert.assertEquals(null, assignedLot);
    }

    @Test
    public void givenAHandicapedDriver_WhenWantToPark_ShouldReturnNearestLot() {
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle);
        ParkingLot assignedLot = parkingLotSystem.getLotOfParkedVehicle(vehicle);
        Assert.assertEquals(parkingLotSystem.parkingLot.get(0), assignedLot);
    }

    @Test
    public void givenAHandicapedDriver_WhenWantToPark_ButLotsAreFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(""));
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(""));
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle1);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ExceptionType.LOTS_FULL, ex.type);
        }
    }

    @Test
    public void givenALargeVehicle_WhenWantsToPark_CheckForVacantSlotsOnAlternateSides_IfNot_ThenParkOnEmptySlot_ShouldReturnLotNumber() {
        try {
            Vehicle vehicle4 = new Vehicle("Purple");
            Vehicle vehicle5 = new Vehicle("White");
            parkingLotSystem = new ParkingLotSystem(5, 2);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
            parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle5);
            parkingLotSystem.parkVehicle(VehicleType.LARGE_VEHICLE, vehicle2);
            ParkingLot assignedLot = parkingLotSystem.getLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(parkingLotSystem.parkingLot.get(1), assignedLot);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ExceptionType.LOTS_FULL, ex.type);
        }
    }

    @Test
    public void givenMultipleVehiclesToPark_ShouldReturnLocationOfWhiteVehicles() {
        List<Integer> temporaryLot1 = new ArrayList();
        List<Integer> temporaryLot2 = new ArrayList();
        temporaryLot1.add(0);
        temporaryLot1.add(2);
        temporaryLot2.add(0);
        List<List<Integer>> temporaryListOfWhiteVehicle = new ArrayList();
        temporaryListOfWhiteVehicle.add(new ArrayList(temporaryLot1));
        temporaryListOfWhiteVehicle.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("Purple");
        Vehicle vehicle5 = new Vehicle("White");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle5);
        List<List<Integer>> overallListOfWhiteVehicles = parkingLotSystem.getOverallLotOfWhiteColorVehicle("White");
        Assert.assertEquals(temporaryListOfWhiteVehicle, overallListOfWhiteVehicles);
    }

    @Test
    public void givenMultipleVehiclesToPark_ShouldReturnDetailsOfBlueColoredToyotaVehicles() {
        List<String> temporaryLot1 = new ArrayList();
        temporaryLot1.add("Slot Number is 1Number Plate MH 43 AR 6210");
        List<String> temporaryLot2 = new ArrayList();
        temporaryLot2.add("Slot Number is 1Number Plate MH 46 KA 5421");
        List<List<String>> temporaryListOfDetailsOfBlueColoredToyotaCars = new ArrayList();
        temporaryListOfDetailsOfBlueColoredToyotaCars.add(new ArrayList(temporaryLot1));
        temporaryListOfDetailsOfBlueColoredToyotaCars.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("Blue", "Toyota", "MH 46 KA 5421");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        List<List<String>> overallListOfBlueColoredToyotaCarsInDifferentLots = parkingLotSystem.getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor("Toyota", "Blue");
        Assert.assertEquals(temporaryListOfDetailsOfBlueColoredToyotaCars, overallListOfBlueColoredToyotaCarsInDifferentLots);
    }
}
