package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    private int totalSlotCapacity;
    private List<ParkingLotObservers> observersList;
    private List vehicleList;
    private List<Integer> unOccupiedSlotList;

    public ParkingLotSystem(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.observersList = new ArrayList<>();
        this.vehicleList = new ArrayList();
        this.unOccupiedSlotList = new ArrayList<>();
    }

    public void initializingSlots() {
        IntStream.range(0, totalSlotCapacity)
                .forEach(slotIndex -> vehicleList.add(null));
    }

    public void parkVehicle(Object vehicle) {
        this.vehicleList.add(vehicle);
    }

    public void parkVehicle(Integer slot, Object vehicle) {
        this.vehicleList.set(slot, vehicle);
        if (!this.vehicleList.contains(null)) {
            for (ParkingLotObservers observer : observersList)
                observer.slotsFull();
            throw new ParkingLotException("Vehicle not parked", ParkingLotException.ExceptionType.SLOT_FULL);
        }
    }

    public boolean checkIfVehicleIsParked(Object vehicle) {
        if (this.vehicleList.contains(vehicle))
            return true;
        throw new ParkingLotException("Vehicle not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);

    }

    public void unparkVehicle(Object vehicle) {
        if (this.vehicleList.contains(vehicle)) {
            this.vehicleList.remove(vehicle);
        }
    }

    public boolean checkIfVehicleIsUnParked() {
        if (this.vehicleList.size() < this.totalSlotCapacity) {
            for (ParkingLotObservers observer : observersList)
                observer.slotsEmpty();
            return true;
        }
        throw new ParkingLotException("Vehicle not unparked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }

    public void setTotalSlotCapacity(int totalSlotCapacity) {
        this.totalSlotCapacity = totalSlotCapacity;
    }

    public List<Integer> getAvailableEmptySlots() {
        IntStream.range(0, totalSlotCapacity)
                .filter(slotIndex -> vehicleList.get(slotIndex) == null)
                .forEach(slotIndex -> unOccupiedSlotList.add(slotIndex));
        return unOccupiedSlotList;
    }

    public int findVehicle(Object vehicle) {
        if (this.vehicleList.contains(vehicle))
            return vehicleList.indexOf(vehicle);
        throw new ParkingLotException("Given vehicle not found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
