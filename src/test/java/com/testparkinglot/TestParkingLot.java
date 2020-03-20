package com.testparkinglot;

import com.parkinglot.ParkingLotSystem;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestParkingLot {
    private static ParkingLotSystem parkingLotSystem;
    private static Object vehicleType;

    @BeforeClass
    public static void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicleType = new Object();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicleType);
        boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked();
        Assert.assertTrue(checkIfVehicleIsParked);
    }

    @Test
    public void givenInitiallyAParkedVehicle_WantsToUnPark_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicleType);
        parkingLotSystem.unparkVehicle(vehicleType);
        boolean checkIfVehicleIsUnParked = parkingLotSystem.checkIfVehicleIsUnParked();
        Assert.assertTrue(checkIfVehicleIsUnParked);
    }
}
