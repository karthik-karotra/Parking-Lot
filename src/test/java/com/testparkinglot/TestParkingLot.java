package com.testparkinglot;

import com.parkinglot.ParkingLotOwner;
import com.parkinglot.ParkingLotSystem;
import com.parkinglotexception.ParkingLotException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicleType;
    private static ParkingLotOwner parkingLotOwner;

    @BeforeClass
    public static void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicleType = new Object();
        parkingLotOwner = new ParkingLotOwner();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.registerLotOwner(parkingLotOwner);
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
        parkingLotSystem.parkVehicle(vehicleType);
        try {
            parkingLotSystem.checkIfVehicleIsParked();
        } catch (ParkingLotException ex) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, ex.type);
        }
        boolean checkIfFull = parkingLotOwner.checkIfSlotIsFull();
        Assert.assertTrue(checkIfFull);
    }
}
