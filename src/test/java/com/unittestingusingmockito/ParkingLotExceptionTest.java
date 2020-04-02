package com.unittestingusingmockito;

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
    }

    /*@Test
    public void testVehicleAlReadyParkedException_WhenVehicleObjectPassedToParkFunction_ThrowAnException() {
        when(parkingLot.parkVehicle(any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0).equals(vehicle)){
                        throw new ParkingLotException("Vehicle already parked",ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionType.SLOT_FULL);
                });
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL_DRIVER);
        }
        catch (ParkingLotException e)
        {
            assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
        }
    }*/
     /*
    @Test
    public void testParkingLotFullException_WhenAnotherObjectPassedToParkFunction_ThrowAnException() {
        when(parkingLot.park(any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0)==vehicle){
                        throw new ParkingLotException("",ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
                });
        try {
            parkingLot.park(new Object(), ParkingLotSystem.DriverType.NORMAL);
        }
        catch (ParkingLotException e)
        {
            assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
        }
    }

    @Test
    public void testVehicleNotFoundException_WhenNewObjectPassedToUnParkFunctionNotMatchesVehicleObject_ThrowVehicleNotFoundException() {
        when(parkingLot.unPark(any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0)==vehicle){
                        return "unParked";
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
                });
        try {
            parkingLot.unPark(new Object());
        }
        catch (ParkingLotException e)
        {
            assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
        }
    }
}*/
}
