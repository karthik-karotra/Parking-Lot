package com.unittestingusingmockito;

import com.enums.DriverType;
import com.parkinglot.ParkingLot;
import com.parkinglot.ParkingLotSystem;
import com.parkinglot.Vehicle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotSystemMockitoTest {
    @Mock
    ParkingLotSystem parkingLotSystem;
    ParkingLot parkingLot;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        vehicle = new Vehicle("");
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void testingParkVehicleFunction() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            return null;
        }).when(parkingLotSystem).parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        boolean isParked = parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        assertTrue(isParked);
    }

    @Test
    public void testingGetLotOfParkedVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.checkIfVehicleIsParked(vehicle);
            return null;
        }).when(parkingLotSystem).getLotOfParkedVehicle(vehicle);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        boolean isParked = parkingLot.checkIfVehicleIsParked(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void testingGetDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor("", "");
            return null;
        }).when(parkingLotSystem).getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor("", "");
        List temporaryList = new ArrayList();
        List listOfBlueToyotaCars = parkingLot.getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor("", "");
        assertEquals(temporaryList, listOfBlueToyotaCars);
    }

    @Test
    public void testingGetOverallListOfCarsInDifferentLotsByFieldOfVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.getDetailsOfCarsInParticularLotByFieldOfVehicle("");
            return null;
        }).when(parkingLotSystem).getOverallListOfCarsInDifferentLotsByFieldOfVehicle("");
        List temporaryList = new ArrayList();
        List listOfCarsByVehicleField = parkingLot.getDetailsOfCarsInParticularLotByFieldOfVehicle("");
        assertEquals(temporaryList, listOfCarsByVehicleField);
    }

    @Test
    public void testingGetListOfAllCarsParkedInLastThirtyMinutesInAllLots() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.getListOfAllCarsParkedInLastThirtyMinutesInParticularLot();
            return null;
        }).when(parkingLotSystem).getListOfAllCarsParkedInLastThirtyMinutesInAllLots();
        List temporaryList = new ArrayList();
        List listOfCarsParkedInLast30Minutes = parkingLot.getListOfAllCarsParkedInLastThirtyMinutesInParticularLot();
        assertEquals(temporaryList, listOfCarsParkedInLast30Minutes);
    }

    @Test
    public void testingGetDetailsOfAllVehiclesInAllLots() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.getDetailsOfAllCarsInParticularLot();
            return null;
        }).when(parkingLotSystem).getDetailsOfAllVehiclesInAllLots();
        List temporaryList = new ArrayList();
        List listOfAllVehicles = parkingLot.getDetailsOfAllCarsInParticularLot();
        assertEquals(temporaryList, listOfAllVehicles);
    }
}
