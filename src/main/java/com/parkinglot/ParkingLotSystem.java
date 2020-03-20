package com.parkinglot;

import com.parkinglotexception.ParkingLotException;

public class ParkingLotSystem {

    private Object vehicleType;
    private final int totalSlotCapacity;
    private int currentSlotCapacity;
    private ParkingLotOwner parkingLotOwner;
    private SecurityPerson securityPerson;

    public ParkingLotSystem(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.currentSlotCapacity = 0;
    }


    public void parkVehicle(Object vehicleType) {
        this.vehicleType = vehicleType;
        this.currentSlotCapacity++;
    }

    public boolean checkIfVehicleIsParked() {
        if (this.totalSlotCapacity == this.currentSlotCapacity) {
            this.parkingLotOwner.slotsFull();
            this.securityPerson.slotsFull();
            throw new ParkingLotException("Vehicle not parked", ParkingLotException.ExceptionType.SLOT_FULL);
        }
        return true;
    }

    public void unparkVehicle(Object vehicleType) {
        if (this.vehicleType.equals(vehicleType)) {
            this.vehicleType = null;
            this.currentSlotCapacity--;
        }
    }

    public boolean checkIfVehicleIsUnParked() {
        if (this.vehicleType == null)
            return true;
        throw new ParkingLotException("Vehicle not unparked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }

    public void registerLotOwner(ParkingLotOwner parkingLotOwner) {
        this.parkingLotOwner = parkingLotOwner;
    }

    public void registerSecurityPerson(SecurityPerson securityPerson) {
        this.securityPerson = securityPerson;
    }
}
