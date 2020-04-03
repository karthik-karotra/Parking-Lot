/*package com.unittestingusingmockito;

import com.parkinglot.DriverType;
import com.parkinglot.ParkingLotOwner;
import com.parkinglot.ParkingLot;
import com.parkinglotexception.ParkingLotException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingLotExceptionTest {
    @Mock
    ParkingLot parkingLot;
    ParkingLotOwner owner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        owner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test(expected = ParkingLotException.class)
    public void testParkingLotExceptionClass_ThrowParkingLotException_WhenCallingParkFunction() {
        doThrow(ParkingLotException.class)
                .when(parkingLot).parkVehicle(any(), any(Object.class));
        parkingLot.parkVehicle(1, DriverType.NORMAL_DRIVER);
    }*/

//}