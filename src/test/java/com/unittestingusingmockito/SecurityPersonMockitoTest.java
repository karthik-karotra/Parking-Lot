package com.unittestingusingmockito;

import com.observers.ParkingLotInformer;
import com.observers.SecurityPerson;
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
    ParkingLotInformer parkingLotInformer;
    SecurityPerson securityPerson;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotInformer = mock(ParkingLotInformer.class);
        securityPerson = new SecurityPerson();
        vehicle = new Vehicle("");
    }

    @Test
    public void testingSlotsFullAndCheckIfParkingSlotIsFullFunction_ForSecurityPerson_WhenLotIsFull() {
        parkingLotInformer.registerObserver(securityPerson);
        doAnswer((Answer<Void>) invocationOnMock -> {
            securityPerson.slotsFull();
            return null;
        }).when(parkingLotInformer).notifyParkingFull();
        parkingLotInformer.notifyParkingFull();
        assertTrue(securityPerson.checkIfParkingSlotIsFull());
    }

    @Test
    public void testingSlotsEmptyAndCheckIfParkingSlotIsFullFunction_ForSecurtiyPerson_WhenLotIsEmpty() {
        parkingLotInformer.registerObserver(securityPerson);
        doAnswer((Answer<Void>) invocationOnMock -> {
            securityPerson.slotsEmpty();
            return null;
        }).when(parkingLotInformer).notifyParkingAvailable();
        parkingLotInformer.notifyParkingAvailable();
        assertFalse(securityPerson.checkIfParkingSlotIsFull());
    }

}


