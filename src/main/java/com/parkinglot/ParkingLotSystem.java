package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import static com.parkinglotexception.ParkingLotException.ExceptionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private int totalSlotCapacity;
    private List<ParkingLotObservers> observersList;
    public List<Slots> slots;

    public ParkingLotSystem(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.observersList = new ArrayList<>();
        this.slots = new ArrayList<>();
    }

    public void initializingSlots() {
        IntStream.range(0, totalSlotCapacity)
                .forEach(slotNumber -> slots.add(new Slots(slotNumber)));
    }

    public void parkVehicle(Object vehicle) {
        List<Integer> availableEmptySlots = getAvailableEmptySlots();
        this.slots.get(availableEmptySlots.get(0)).setParkingTimeOfVehicle(vehicle);
    }

    public void parkVehicle(Integer slot, Object vehicle) {
        try {
            this.slots.get(slot).setParkingTimeOfVehicle(vehicle);
        } catch (IndexOutOfBoundsException ex) {
            throw new ParkingLotException("Slot unavailable", ExceptionType.SLOT_FULL);
        }

    }

    public boolean checkIfVehicleIsParked(Object vehicle) {
        try {
            boolean whetherParked = this.slots.stream()
                    .anyMatch(slot -> slot.getVehicle().equals(vehicle));
            return whetherParked;
        } catch (NullPointerException ex) {
            throw new ParkingLotException("Vehicle not parked", ExceptionType.VEHICLE_NOT_PARKED);
        }
    }

    public void unparkVehicle(Object vehicle) {
        Slots slots = this.slots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_FOUND));
        slots.setParkingTimeOfVehicle(null);
        for (ParkingLotObservers observer : observersList)
            observer.slotsEmpty();
    }

    public boolean checkIfVehicleIsUnParked(Object vehicle) {
        boolean isPresent = this.slots.stream()
                .filter(slot -> slot.getVehicle() == (vehicle))
                .findFirst()
                .isPresent();
        if (!isPresent)
            return true;
        throw new ParkingLotException("Vehicle not unparked", ExceptionType.VEHICLE_NOT_UNPARKED);
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }

    public void setTotalSlotCapacity(int totalSlotCapacity) {
        this.totalSlotCapacity = totalSlotCapacity;
    }

    public List<Integer> getAvailableEmptySlots() {
        List<Integer> unOccupiedSlotList = new ArrayList();
        IntStream.range(0, totalSlotCapacity)
                .filter(slotIndex -> this.slots.get(slotIndex).getVehicle() == null)
                .forEach(slotIndex -> unOccupiedSlotList.add(slotIndex));
        if (unOccupiedSlotList.size() == 0) {
            for (ParkingLotObservers observer : observersList)
                observer.slotsFull();
            throw new ParkingLotException("Slots are full", ExceptionType.SLOT_FULL);
        }
        return unOccupiedSlotList;
    }

    public int findVehicle(Object vehicle) {
        Slots slots = this.slots.stream()
                .filter(slot -> slot.getVehicle() == vehicle)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_FOUND));
        return slots.getSlotNumber();
    }

    public LocalDateTime getParkingTimeOfVehicle(Object vehicle) {
        return this.slots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTimeOfParking();
    }
}
