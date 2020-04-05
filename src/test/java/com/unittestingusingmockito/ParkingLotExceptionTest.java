package com.unittestingusingmockito;

import com.enums.DriverType;
import com.observers.ParkingLotOwner;
import com.parkinglot.ParkingLot;
import com.parkinglot.Vehicle;
import com.parkinglotexception.ParkingLotException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingLotExceptionTest {
    @Mock
    ParkingLot parkingLot;
    ParkingLotOwner owner;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        owner = new ParkingLotOwner();
        vehicle = new Vehicle("");
    }

    @Test(expected = ParkingLotException.class)
    public void testingParkingLotExceptionClass_WhenParkFunctionIsCalled_ShouldThrowParkingLotException() {
        doThrow(ParkingLotException.class)
                .when(parkingLot).parkVehicle(any(Enum.class), any(Vehicle.class));
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
    }

    @Test
    public void testingVehicleAlReadyParkedException_WhenVehicleIsPassedToParkFunction_ShouldThrowAnException() {
        when(parkingLot.parkVehicle(any(Enum.class), any(Vehicle.class))).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0).equals(Vehicle.class)) {
                        throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.SLOT_FULL);
                });
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        } catch (ParkingLotException ex) {
            assertEquals(ex.type, ParkingLotException.ExceptionType.SLOT_FULL);
        }
    }

    @Test
    public void testingSLotFullException_WhenAVehiclePassedToParkFunction_ShouldThrowAnException() {
        when(parkingLot.parkVehicle(any(Enum.class), any(Vehicle.class))).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.SLOT_FULL);
                });
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle("Red"));
        } catch (ParkingLotException ex) {
            assertEquals(ex.type, ParkingLotException.ExceptionType.SLOT_FULL);
        }
    }

    @Test
    public void testingVehicleNotFoundException_WhenAVehicleIsPassedToUnParkFunctionButVehicleIsNotFound_ShouldThrowVehicleNotFoundException() {
        when(parkingLot.parkVehicle(any(Enum.class), any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        return "unParked";
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
                });
        try {
            parkingLot.unparkVehicle(new Vehicle("Red"));
        } catch (ParkingLotException ex) {
            assertEquals(ex.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }
}

