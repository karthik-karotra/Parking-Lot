package com.parkinglot;

import com.parkinglotexception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {

    private int totalSlotCapacity;
    private int currentSlotCapacity;
    private List<ParkingLotObservers> observersList;
    private List vehicleList;

    public ParkingLotSystem(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.currentSlotCapacity = 0;
        this.observersList = new ArrayList<>();
        this.vehicleList=new ArrayList();
    }

    public void parkVehicle(Object vehicle) {
        this.vehicleList.add(vehicle);
       // this.currentSlotCapacity++;
    }

    public boolean checkIfVehicleIsParked() {
        if (this.totalSlotCapacity == vehicleList.size()) {
            for (ParkingLotObservers observer : observersList)
                observer.slotsFull();
            throw new ParkingLotException("Vehicle not parked", ParkingLotException.ExceptionType.SLOT_FULL);
        }
        return true;
    }

    public void unparkVehicle(Object vehicle) {
        if (this.vehicleList.contains(vehicle)) {
            this.vehicleList.remove(vehicle);
           // this.currentSlotCapacity--;
        }
    }

    public boolean checkIfVehicleIsUnParked() {
        if (this.vehicleList.size() < this.totalSlotCapacity) {
            for(ParkingLotObservers observer:observersList)
                observer.slotsEmpty();
            return true;
        }
        throw new ParkingLotException("Vehicle not unparked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }

    public void setTotalSlotCapacity(int totalSlotCapacity) {
        this.totalSlotCapacity=totalSlotCapacity;
    }
}
