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
        vehicleType=new Object();
    }

    @Test
    public void givenInitiallyAVehicle_WantsToPark_ShouldReturnTrue() {
        parkingLotSystem.getParked(new Object());
        boolean checkIfVehicleIsParked = parkingLotSystem.checkIfVehicleIsParked();
        Assert.assertTrue(checkIfVehicleIsParked);
    }
}
