package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import static com.parkinglotexception.ParkingLotException.ExceptionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot implements ParkingLotDao {

    private Integer totalSlotCapacity;
    private List<ParkingLotObservers> observersList;
    private List<Slots> slots;
    private Integer noOfVehicles = 0;

    public ParkingLot(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.observersList = new ArrayList<>();
        this.slots = new ArrayList<>();
        this.initializingSlots();
    }

    @Override
    public void initializingSlots() {
        IntStream.range(0, totalSlotCapacity).forEach(slotNumber -> slots.add(new Slots(slotNumber)));
    }

    @Override
    public boolean parkVehicle(Object vehicle) {
        List<Integer> availableEmptySlots = getAvailableEmptySlots();
        if (availableEmptySlots.size() == 0)
            throw new ParkingLotException("Slots full", ExceptionType.SLOT_FULL);
        this.slots.get(availableEmptySlots.get(0)).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
        return true;
    }

    public boolean parkVehicle(VehicleType vehicleType, Object vehicle) {
        List<Integer> availableEmptySlots = getAvailableEmptySlots();
        if (availableEmptySlots.size() == 0)
            throw new ParkingLotException("Slots full", ExceptionType.SLOT_FULL);
        else if (vehicleType.equals(VehicleType.LARGE_VEHICLE)) {
            if (availableEmptySlots.size() > 2)
                this.slots.get(availableEmptySlots.get(1)).setParkingTimeOfVehicle(vehicle);
        }
        this.slots.get(availableEmptySlots.get(0)).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
        return true;
    }

    @Override
    public void parkVehicle(Integer slot, Object vehicle) {
        this.slots.get(slot).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
    }

    @Override
    public boolean checkIfVehicleIsParked(Object vehicle) {
        try {
            boolean whetherParked = this.slots.stream()
                    .anyMatch(slot -> vehicle.equals(slot.getVehicle()));
            return whetherParked;
        } catch (NullPointerException ex) {
            throw new ParkingLotException("Vehicle not parked", ExceptionType.VEHICLE_NOT_PARKED);
        }
    }

    @Override
    public void unparkVehicle(Object vehicle) {
        Slots slots = this.slots.stream()
                .filter(slot -> vehicle.equals(slot.getVehicle()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_UNPARKED));
        slots.setParkingTimeOfVehicle(null);
        for (ParkingLotObservers observer : observersList)
            observer.slotsEmpty();
        noOfVehicles--;
    }

    @Override
    public boolean checkIfVehicleIsUnParked(Object vehicle) {
        boolean isPresent = this.slots.stream()
                .filter(slot -> slot.getVehicle() == (vehicle))
                .findFirst()
                .isPresent();
        if (!isPresent)
            return true;
        throw new ParkingLotException("Vehicle not unparked", ExceptionType.VEHICLE_NOT_FOUND);
    }

    @Override
    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }

    @Override
    public void setTotalSlotCapacity(int totalSlotCapacity) {
        this.totalSlotCapacity = totalSlotCapacity;
    }

    @Override
    public List<Integer> getAvailableEmptySlots() {

        List<Integer> unOccupiedSlotList = new ArrayList();
        try {
            IntStream.range(0, totalSlotCapacity)
                    .filter(slotIndex -> this.slots.get(slotIndex).getVehicle() == null)
                    .forEach(slotIndex -> unOccupiedSlotList.add(slotIndex));
            if (unOccupiedSlotList.size() == 0) {
                for (ParkingLotObservers observer : observersList)
                    observer.slotsFull();
                throw new ParkingLotException("Slots are full", ExceptionType.LOTS_FULL);
            }
        } catch (ParkingLotException e) {
        }
        return unOccupiedSlotList;
    }

    @Override
    public Integer findVehicle(Object vehicle) {
        Slots slots = this.slots.stream()
                .filter(slot -> slot.getVehicle() == vehicle)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_FOUND));
        return slots.getSlotNumber();
    }

    @Override
    public Integer getCountOfVehiclesParked() {
        return this.noOfVehicles;
    }

    @Override
    public LocalDateTime getParkingTimeOfVehicle(Object vehicle) {
        return this.slots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTimeOfParking();
    }
}
