package com.testparkinglot;

import com.enums.DriverType;
import com.enums.VehicleType;
import com.observers.ParkingLotObservers;
import com.observers.ParkingLotOwner;
import com.observers.SecurityPerson;
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
        vehicle = new Vehicle("White", "BMW", "MH 43 AR 6451", "Normal");
        vehicle1 = new Vehicle("White", "Toyota", "MH 43 AV 7854", "Normal");
        vehicle2 = new Vehicle("Blue", "Toyota", "MH 43 AR 6210", "Normal");
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
    public void givenANullVehicle_WantsToParkButCouldNotPark_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, null);
            boolean checkIfVehicleIsParked = parkingLot.checkIfVehicleIsParked(null);
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
    public void givenAParkedVehicle_WantsToCheckWheterUnparked_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            boolean checkIfVehicleIsUnParked = parkingLot.checkIfVehicleIsUnParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, ex.type);
        }
    }

    @Test
    public void givenAVehicle_WantsToUnparked_ButVehicleNotFound_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
             parkingLot.unparkVehicle(vehicle1);
            boolean checkIfVehicleIsUnParked = parkingLot.checkIfVehicleIsUnParked(vehicle);
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ExceptionType.VEHICLE_NOT_FOUND, ex.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLot.setTotalParkingSlotCapacity(1);
            parkingLot.initializingParkingSlots();
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
            boolean checkIfParkingSlotIsFull = parkingLotOwner.checkIfParkingSlotIsFull();
            Assert.assertTrue(checkIfParkingSlotIsFull);
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
            boolean checkIfParkingSlotIsFull = securityPerson.checkIfParkingSlotIsFull();
            Assert.assertTrue(checkIfParkingSlotIsFull);
        }
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheOwner() {
        parkingLot.registerObserver(parkingLotOwner);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.unparkVehicle(vehicle);
        parkingLot.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(parkingLotOwner.checkIfParkingSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenEmpty_ShouldInformTheSecurityPerson() {
        parkingLot.registerObserver(securityPerson);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.unparkVehicle(vehicle);
        parkingLot.checkIfVehicleIsUnParked(vehicle);
        Assert.assertFalse(securityPerson.checkIfParkingSlotIsFull());
    }

    @Test
    public void givenAParkingLot_WhenGivenMultipleVehiclesToPark_MoreThanItsCapacity_ShouldThrowException() {
        parkingLot.setTotalParkingSlotCapacity(2);
        parkingLot.initializingParkingSlots();
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
    public void givenAnEmptyParkingLot_WillReturnListOfAvailableParkingSlots() {
        List<Integer> expectedEmptyParkingSlotList = new ArrayList<>();
        expectedEmptyParkingSlotList.add(0);
        expectedEmptyParkingSlotList.add(1);
        parkingLot.setTotalParkingSlotCapacity(2);
        parkingLot.initializingParkingSlots();
        List<Integer> availableEmptyParkingSlotList = parkingLot.getAvailableEmptyParkingSlots();
        Assert.assertEquals(expectedEmptyParkingSlotList, availableEmptyParkingSlotList);
    }

    @Test
    public void givenAParkingLot_PartiallyOccupied_AttendantWillParkTheVehicleInTheAvailableEmptyParkingSlot() {
        parkingLot.setTotalParkingSlotCapacity(2);
        parkingLot.initializingParkingSlots();
        List<Integer> availableEmptyParkingSlotList = parkingLot.getAvailableEmptyParkingSlots();
        parkingLot.parkVehicle(availableEmptyParkingSlotList.get(0), vehicle);
        boolean checkIfVehicleIsParked = parkingLot.checkIfVehicleIsParked(vehicle);
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenAParkingLot_DriverWantsToFindHisVehicle_IfFound_ShouldReturnCorrectParkedParkingSlot() {
        parkingLot.setTotalParkingSlotCapacity(2);
        parkingLot.initializingParkingSlots();
        List<Integer> availableEmptyParkingSlotList = parkingLot.getAvailableEmptyParkingSlots();
        parkingLot.parkVehicle(availableEmptyParkingSlotList.get(0), vehicle);
        int slotPositionOfParkedVehicle = parkingLot.findVehicle(vehicle);
        Assert.assertEquals(0, slotPositionOfParkedVehicle);
    }

    @Test
    public void givenAParkingLot_DriverWantToFindHisVehicle_IfNotFound_ShouldThrowException() {
        parkingLot.setTotalParkingSlotCapacity(2);
        parkingLot.initializingParkingSlots();
        try {
            parkingLot.getAvailableEmptyParkingSlots();
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
        List<String> temporaryLot1 = new ArrayList();
        List<String> temporaryLot2 = new ArrayList();
        temporaryLot1.add("ParkingSlot Number is 0, Number Plate MH 46 KA 5421, Vehicle Name Toyota, Color White");
        temporaryLot1.add("ParkingSlot Number is 2, Number Plate MH 49 DY 8458, Vehicle Name Scorpio, Color White");
        temporaryLot2.add("ParkingSlot Number is 0, Number Plate MH 47 DE 4158, Vehicle Name Scorpio, Color White");
        List<List<Integer>> temporaryListOfWhiteVehicle = new ArrayList();
        temporaryListOfWhiteVehicle.add(new ArrayList(temporaryLot1));
        temporaryListOfWhiteVehicle.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("White", "Toyota", "MH 46 KA 5421", "Normal");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158", "Normal");
        Vehicle vehicle6 = new Vehicle("Red", "Toyoya", "MH 43 HE 4858", "Normal");
        Vehicle vehicle7 = new Vehicle("Blue", "Scorpio", "MH 49 DY 8458", "Normal");
        Vehicle vehicle8 = new Vehicle("White", "Scorpio", "MH 49 DY 8458", "Handicape");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle6);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle7);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle8);
        List<List<String>> overallListOfWhiteVehicles = parkingLotSystem.getOverallListOfCarsInDifferentLotsByFieldOfVehicle("White");
        Assert.assertEquals(temporaryListOfWhiteVehicle, overallListOfWhiteVehicles);
    }

    @Test
    public void givenMultipleVehiclesToPark_ShouldReturnDetailsOfBlueColoredToyotaVehicles() {
        List<String> temporaryLot1 = new ArrayList();
        temporaryLot1.add("ParkingSlot Number is 1Number Plate MH 43 AR 6210");
        List<String> temporaryLot2 = new ArrayList();
        temporaryLot2.add("ParkingSlot Number is 1Number Plate MH 46 KA 5421");
        List<List<String>> temporaryListOfDetailsOfBlueColoredToyotaCars = new ArrayList();
        temporaryListOfDetailsOfBlueColoredToyotaCars.add(new ArrayList(temporaryLot1));
        temporaryListOfDetailsOfBlueColoredToyotaCars.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("Blue", "Toyota", "MH 46 KA 5421", "Normal");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158", "Normal");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        List<List<String>> overallListOfBlueColoredToyotaCarsInDifferentLots = parkingLotSystem.getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor("Toyota", "Blue");
        Assert.assertEquals(temporaryListOfDetailsOfBlueColoredToyotaCars, overallListOfBlueColoredToyotaCarsInDifferentLots);
    }

    @Test
    public void givenMultipleVehiclesToPark_ShouldReturnLocationBMWCars() {
        List<String> temporaryLot1 = new ArrayList();
        temporaryLot1.add("ParkingSlot Number is 0, Number Plate MH 43 AR 6451, Vehicle Name BMW, Color White");
        List<String> temporaryLot2 = new ArrayList();
        temporaryLot2.add("ParkingSlot Number is 1, Number Plate MH 46 KA 5421, Vehicle Name BMW, Color Blue");
        List<List<String>> temporaryListOfLocationOfBMWCars = new ArrayList();
        temporaryListOfLocationOfBMWCars.add(new ArrayList(temporaryLot1));
        temporaryListOfLocationOfBMWCars.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("Blue", "BMW", "MH 46 KA 5421", "Normal");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158", "Handicape");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle5);
        List<List<String>> overallListOfBMWCars = parkingLotSystem.getOverallListOfCarsInDifferentLotsByFieldOfVehicle("BMW");
        Assert.assertEquals(temporaryListOfLocationOfBMWCars, overallListOfBMWCars);
    }

    @Test
    public void GivenMultipleVehiclesToPark_ShouldReturnListOfVehiclesParkedUnLastThirtyMinutes() {
        Vehicle vehicle4 = new Vehicle("Blue", "BMW", "MH 46 KA 5421", "Normal");
        List<Vehicle> temporaryLot1 = new ArrayList();
        temporaryLot1.add(vehicle);
        temporaryLot1.add(vehicle2);
        List<Vehicle> temporaryLot2 = new ArrayList();
        temporaryLot2.add(vehicle1);
        temporaryLot2.add(vehicle4);
        List<List<String>> temporaryListOfAllCarsParkedInLastThirtyMinutes = new ArrayList();
        temporaryListOfAllCarsParkedInLastThirtyMinutes.add(new ArrayList(temporaryLot1));
        temporaryListOfAllCarsParkedInLastThirtyMinutes.add(new ArrayList(temporaryLot2));
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        List<List<Vehicle>> overallListOfAllCarsParkedInLastThirtyMinutes = parkingLotSystem.getListOfAllCarsParkedInLastThirtyMinutesInAllLots();
        Assert.assertEquals(temporaryListOfAllCarsParkedInLastThirtyMinutes, overallListOfAllCarsParkedInLastThirtyMinutes);
    }

    @Test
    public void givenMultipleHandicapeDriversCars_ShouldReturnInformationOfCars() {
        List<String> temporaryLot1 = new ArrayList();
        temporaryLot1.add("ParkingSlot Number is 1, Number Plate MH 47 DE 4158, Vehicle Name Scorpio, Color White");
        temporaryLot1.add("ParkingSlot Number is 2, Number Plate MH 49 DY 8458, Vehicle Name Scorpio, Color White");
        Vehicle vehicle4 = new Vehicle("Blue", "Toyota", "MH 46 KA 5421", "Normal");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158", "Handicape");
        Vehicle vehicle6 = new Vehicle("Red", "Toyoya", "MH 43 HE 4858", "Normal");
        Vehicle vehicle7 = new Vehicle("White", "Scorpio", "MH 49 DY 8458", "Handicape");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle5);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle6);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle7);
        List<List<String>> overallListOFHandicapedDriversCarsInDifferentLots = parkingLotSystem.getOverallListOfCarsInDifferentLotsByFieldOfVehicle("Handicape");
        Assert.assertEquals(temporaryLot1, overallListOFHandicapedDriversCarsInDifferentLots.get(0));
    }

    @Test
    public void givenMultipleCarsToPark_ShouldReturnInformationOfAllCars() {
        List<String> temporaryLot1 = new ArrayList();
        temporaryLot1.add("ParkingSlot Number is 0, Number Plate MH 46 KA 5421, Vehicle Name Toyota, Color Blue, Driver Type Normal");
        temporaryLot1.add("ParkingSlot Number is 1, Number Plate MH 47 DE 4158, Vehicle Name Scorpio, Color White, Driver Type Handicape");
        temporaryLot1.add("ParkingSlot Number is 2, Number Plate MH 49 DY 8458, Vehicle Name Scorpio, Color White, Driver Type Handicape");
        List<String> temporaryLot2 = new ArrayList();
        temporaryLot2.add("ParkingSlot Number is 0, Number Plate MH 43 HE 4858, Vehicle Name Toyoya, Color Red, Driver Type Normal");
        List<List<String>> temporaryListOfDetailsOfAllCars = new ArrayList();
        temporaryListOfDetailsOfAllCars.add(new ArrayList(temporaryLot1));
        temporaryListOfDetailsOfAllCars.add(new ArrayList(temporaryLot2));
        Vehicle vehicle4 = new Vehicle("Blue", "Toyota", "MH 46 KA 5421", "Normal");
        Vehicle vehicle5 = new Vehicle("White", "Scorpio", "MH 47 DE 4158", "Handicape");
        Vehicle vehicle6 = new Vehicle("Red", "Toyoya", "MH 43 HE 4858", "Normal");
        Vehicle vehicle7 = new Vehicle("White", "Scorpio", "MH 49 DY 8458", "Handicape");
        parkingLotSystem = new ParkingLotSystem(5, 2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle5);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle6);
        parkingLotSystem.parkVehicle(DriverType.HANDICAPE_DRIVER, vehicle7);
        List<List<String>> overallListOfBlueColoredToyotaCarsInDifferentLots = parkingLotSystem.getDetailsOfAllVehiclesInAllLots();
        Assert.assertEquals(temporaryListOfDetailsOfAllCars, overallListOfBlueColoredToyotaCarsInDifferentLots);
    }
}
