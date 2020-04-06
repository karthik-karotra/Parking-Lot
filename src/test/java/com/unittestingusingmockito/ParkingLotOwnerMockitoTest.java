package com.unittestingusingmockito;

import com.observers.ParkingLotInformer;
import com.observers.ParkingLotOwner;
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

public class ParkingLotOwnerMockitoTest {

    @Mock
    ParkingLotInformer parkingLotInformer;
    ParkingLotOwner parkingLotOwner;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotInformer = mock(ParkingLotInformer.class);
        parkingLotOwner = new ParkingLotOwner();
        vehicle = new Vehicle("");
    }

    @Test
    public void testingSlotsFullAndCheckIfParkingSlotIsFullFunction_ForParkingLotOwner_WhenLotIsFull() {
        parkingLotInformer.registerObserver(parkingLotOwner);
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.slotsFull();
            return null;
        }).when(parkingLotInformer).notifyParkingFull();
        parkingLotInformer.notifyParkingFull();
        assertTrue(parkingLotOwner.checkIfParkingSlotIsFull());
    }

    @Test
    public void testingSlotsEmptyAndCheckIfParkingSlotIsFullFunction_ForParkingLotOwner_WhenLotIsEmpty() {
        parkingLotInformer.registerObserver(parkingLotOwner);
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.slotsEmpty();
            return null;
        }).when(parkingLotInformer).notifyParkingAvailable();
        parkingLotInformer.notifyParkingAvailable();
        assertFalse(parkingLotOwner.checkIfParkingSlotIsFull());
    }
}


