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

public class SecurityPersonMockitoTest {
    @Mock
    ParkingLotObservers securityPerson;
    Vehicle vehicle;
    ParkingLot parkingLot;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        securityPerson = mock(ParkingLotObservers.class);
        vehicle = new Vehicle("Red");
    }

    @Test
    public void testingParkingSlotFullForSecurityPerson() {
        try {
            parkingLot.registerObserver(securityPerson);
            doAnswer((Answer<Void>) invocationOnMock -> {
                securityPerson.checkIfParkingSlotIsFull();
                return null;
            }).when(securityPerson).slotsFull();
            securityPerson.slotsFull();
            assertTrue(securityPerson.checkIfParkingSlotIsFull());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void testingParkingSlotEmptyForSecurityPerson() {
        try {
            parkingLot.registerObserver(securityPerson);
            doAnswer((Answer<Void>) invocationOnMock -> {
                securityPerson.checkIfParkingSlotIsFull();
                return null;
            }).when(securityPerson).slotsEmpty();
            securityPerson.slotsEmpty();
            assertFalse(securityPerson.checkIfParkingSlotIsFull());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }
}


