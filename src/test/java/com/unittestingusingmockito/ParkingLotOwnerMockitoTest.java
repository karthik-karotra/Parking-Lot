package com.unittestingusingmockito;

import com.observers.ParkingLotObservers;
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

public class ParkingLotOwnerMockitoTest {

    @Mock
    ParkingLotObservers owner;
    Vehicle vehicle;
    ParkingLot parkingLot;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        owner = mock(ParkingLotObservers.class);
        vehicle = new Vehicle("");
    }

    @Test
    public void testingParkingSlotFullForOwner() {
        try {
            parkingLot.registerObserver(owner);
            doAnswer((Answer<Void>) invocationOnMock -> {
                owner.slotsFull();
                return null;
            }).when(owner).checkIfParkingSlotIsFull();
            owner.slotsFull();
            assertTrue(owner.checkIfParkingSlotIsFull());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void testingParkingSlotEmptyForOwner() {
        try {
            parkingLot.registerObserver(owner);
            doAnswer((Answer<Void>) invocationOnMock -> {
                owner.slotsEmpty();
                return null;
            }).when(owner).checkIfParkingSlotIsFull();
            owner.slotsEmpty();
            assertFalse(owner.checkIfParkingSlotIsFull());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }
}


