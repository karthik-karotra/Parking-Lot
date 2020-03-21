package com.parkinglot;

import com.parkinglotexception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {

    private Object vehicleType;
    private final int totalSlotCapacity;
    private int currentSlotCapacity;
    private List<ParkingLotObservers> observersList;

    public ParkingLotSystem(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.currentSlotCapacity = 0;
        this.observersList = new ArrayList<>();
    }


    public void parkVehicle(Object vehicleType) {
        this.vehicleType = vehicleType;
        this.currentSlotCapacity++;
    }

    public boolean checkIfVehicleIsParked() {
        if (this.totalSlotCapacity == this.currentSlotCapacity) {
            for (ParkingLotObservers observer : observersList)
                observer.slotsFull();
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
        if (this.vehicleType == null) {
            for(ParkingLotObservers observer:observersList)
                observer.slotsEmpty();
            return true;
        }
        throw new ParkingLotException("Vehicle not unparked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }
}
