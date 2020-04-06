package com.unittestingusingmockito;

import com.enums.DriverType;
import com.observers.ParkingLotInformer;
import com.observers.ParkingLotOwner;
import com.observers.SecurityPerson;
import com.parkinglot.ParkingLot;
import com.parkinglot.Vehicle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotInformerMockitoTest {

    @Mock
    ParkingLot parkingLot;
    ParkingLotInformer parkingLotInformer;
    ParkingLotOwner parkingLotOwner;
    SecurityPerson securityPerson;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        securityPerson = new SecurityPerson();
        parkingLotOwner = new ParkingLotOwner();
        parkingLotInformer = ParkingLotInformer.getObjectOfParkingLotInformer();
        vehicle = new Vehicle("");
    }

    @Test
    public void testingNotifyParkingAvailable_ForOwnerAndSecurityPerson() {
        parkingLotInformer.registerObserver(parkingLotOwner);
        parkingLotInformer.registerObserver(securityPerson);
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotInformer.notifyParkingAvailable();
            return null;
        }).when(parkingLot).parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        assertFalse(parkingLotOwner.checkIfParkingSlotIsFull() && securityPerson.checkIfParkingSlotIsFull());
    }

    @Test
    public void testingNotifyParkingFull_ForOwnerAndSecurityPerson() {
        parkingLotInformer.registerObserver(parkingLotOwner);
        parkingLotInformer.registerObserver(securityPerson);
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotInformer.notifyParkingFull();
            return null;
        }).when(parkingLot).parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        assertTrue(parkingLotOwner.checkIfParkingSlotIsFull() && securityPerson.checkIfParkingSlotIsFull());
    }
}
