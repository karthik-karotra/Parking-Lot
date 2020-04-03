package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import static com.parkinglotexception.ParkingLotException.ExceptionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot implements ParkingLotDao {

    private Integer totalSlotCapacity;
    private List<ParkingLotObservers> observersList;
    private List<Slots> slots;
    private Integer noOfVehicles = 0;

    public ParkingLot(Integer slotCapacity) {
        this.totalSlotCapacity = slotCapacity;
        this.observersList = new ArrayList();
        this.slots = new ArrayList();
        this.initializingSlots();
    }

    @Override
    public void initializingSlots() {
        IntStream.range(0, totalSlotCapacity).forEach(slotNumber -> slots.add(new Slots(slotNumber)));
    }

    @Override
    public boolean parkVehicle(Enum strategyType, Vehicle vehicle) {
        List<Integer> availableEmptySlots = getAvailableEmptySlots();
        if (availableEmptySlots.size() == 0)
            throw new ParkingLotException("Slots full", ExceptionType.SLOT_FULL);
        else if (strategyType.equals(VehicleType.LARGE_VEHICLE)) {
            if (availableEmptySlots.size() > 2)
                this.slots.get(availableEmptySlots.get(1)).setParkingTimeOfVehicle(vehicle);
        }
        this.slots.get(availableEmptySlots.get(0)).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
        return true;
    }

    @Override
    public void parkVehicle(Integer slot, Vehicle vehicle) {
        this.slots.get(slot).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
    }

    @Override
    public boolean checkIfVehicleIsParked(Vehicle vehicle) {
        try {
            boolean whetherParked = this.slots.stream()
                    .anyMatch(slot -> vehicle.equals(slot.getVehicle()));
            return whetherParked;
        } catch (NullPointerException ex) {
            throw new ParkingLotException("Vehicle not parked", ExceptionType.VEHICLE_NOT_PARKED);
        }
    }

    @Override
    public void unparkVehicle(Vehicle vehicle) {
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
    public boolean checkIfVehicleIsUnParked(Vehicle vehicle) {
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
    public Integer findVehicle(Vehicle vehicle) {
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
    public LocalDateTime getParkingTimeOfVehicle(Vehicle vehicle) {
        return this.slots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTimeOfParking();
    }

    @Override
    public List<Integer> getListOfWhiteVehiclesInParticularLotByColor(String color) {
        List<Integer> listOfWhiteVehicles = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getColorOfVehicle().equals(color))
                .map(slot -> slot.getSlotNumber())
                .collect(Collectors.toList());
        return listOfWhiteVehicles;
    }

    @Override
    public List<String> getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor(String vehicleName, String color) {
        List<String> listOfDetailsOfBlueColoredToyotaCars = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getColorOfVehicle().equals(color))
                .filter(slot -> slot.getVehicle().getNameOfVehicle().equals(vehicleName))
                .map(slot -> ("Slot Number is " + slot.getSlotNumber() + "Number Plate " + slot.vehicle.getNumberPlateOfVehicle()))
                .collect(Collectors.toList());
        return listOfDetailsOfBlueColoredToyotaCars;
    }

    @Override
    public List<String> getListOfBMWCarsInParticularLotByVehicleName(String vehicleName) {
        List<String> listOfBMWCars = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getNameOfVehicle().equals(vehicleName))
                .map(slot -> ("Slot Number is " + slot.getSlotNumber()))
                .collect(Collectors.toList());
        return listOfBMWCars;
    }

    @Override
    public List<Vehicle> getListOfAllCarsParkedInLastThirtyMinutesInParticularLot() {
        List<Vehicle> listOfAllCArsParkedInThirtyMinutesInParticularLot = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> (LocalDateTime.now().getMinute() - slot.getTimeOfParking().getMinute() >= 0 && LocalDateTime.now().getMinute() - slot.getTimeOfParking().getMinute() <= 30))
                .map(slot -> slot.getVehicle()).collect(Collectors.toList());
        return listOfAllCArsParkedInThirtyMinutesInParticularLot;
    }
}
