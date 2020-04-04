package com.parkinglot;

import com.enums.VehicleType;
import com.observers.ParkingLotObservers;
import com.parkinglotexception.ParkingLotException;
import static com.parkinglotexception.ParkingLotException.ExceptionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {

    private Integer totalParkingSlotCapacity;
    private List<ParkingLotObservers> observersList;
    private List<ParkingSlots> slots;
    private Integer noOfVehicles = 0;

    public ParkingLot(Integer slotCapacity) {
        this.totalParkingSlotCapacity = slotCapacity;
        this.observersList = new ArrayList();
        this.slots = new ArrayList();
        this.initializingParkingSlots();
    }

    public void initializingParkingSlots() {
        IntStream.range(0, totalParkingSlotCapacity).forEach(slotNumber -> slots.add(new ParkingSlots(slotNumber)));
    }

    public boolean parkVehicle(Enum strategyType, Vehicle vehicle) {
        List<Integer> availableEmptyParkingSlots = getAvailableEmptyParkingSlots();
        if (availableEmptyParkingSlots.size() == 0)
            throw new ParkingLotException("ParkingSlots full", ExceptionType.SLOT_FULL);
        else if (strategyType.equals(VehicleType.LARGE_VEHICLE)) {
            if (availableEmptyParkingSlots.size() > 2) {
                this.slots.get(availableEmptyParkingSlots.get(1)).setParkingTimeOfVehicle(vehicle);
                noOfVehicles++;
                return true;
            }
        }
        this.slots.get(availableEmptyParkingSlots.get(0)).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
        return true;
    }

    public void parkVehicle(Integer slot, Vehicle vehicle) {
        this.slots.get(slot).setParkingTimeOfVehicle(vehicle);
        noOfVehicles++;
    }

    public boolean checkIfVehicleIsParked(Vehicle vehicle) {
        try {
            boolean whetherParked = this.slots.stream().anyMatch(slot -> vehicle.equals(slot.getVehicle()));
            return whetherParked;
        } catch (NullPointerException ex) {
            throw new ParkingLotException("Vehicle not parked", ExceptionType.VEHICLE_NOT_PARKED);
        }
    }

    public void unparkVehicle(Vehicle vehicle) {
        ParkingSlots slots = this.slots.stream().filter(slot -> vehicle.equals(slot.getVehicle())).findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_UNPARKED));
        slots.setParkingTimeOfVehicle(null);
        for (ParkingLotObservers observer : observersList)
            observer.slotsEmpty();
        noOfVehicles--;
    }

    public boolean checkIfVehicleIsUnParked(Vehicle vehicle) {
        boolean isPresent = this.slots.stream().filter(slot -> slot.getVehicle() == (vehicle)).findFirst().isPresent();
        if (!isPresent)
            return true;
        throw new ParkingLotException("Vehicle not unparked", ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void registerObserver(ParkingLotObservers observer) {
        observersList.add(observer);
    }

    public void setTotalParkingSlotCapacity(int totalParkingSlotCapacity) {
        this.totalParkingSlotCapacity = totalParkingSlotCapacity;
    }

    public List<Integer> getAvailableEmptyParkingSlots() {
        List<Integer> unOccupiedParkingSlotList = new ArrayList();
        try {
            IntStream.range(0, totalParkingSlotCapacity)
                    .filter(slotIndex -> this.slots.get(slotIndex).getVehicle() == null)
                    .forEach(slotIndex -> unOccupiedParkingSlotList.add(slotIndex));
            if (unOccupiedParkingSlotList.size() == 0) {
                for (ParkingLotObservers observer : observersList)
                    observer.slotsFull();
                throw new ParkingLotException("ParkingSlots are full", ExceptionType.LOTS_FULL);
            }
        } catch (ParkingLotException e) {
        }
        return unOccupiedParkingSlotList;
    }

    public Integer findVehicle(Vehicle vehicle) {
        ParkingSlots slots = this.slots.stream()
                .filter(slot -> slot.getVehicle() == vehicle)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Vehicle not found", ExceptionType.VEHICLE_NOT_FOUND));
        return slots.getParkingSlotNumber();
    }

    public Integer getCountOfVehiclesParked() {
        return this.noOfVehicles;
    }

    public LocalDateTime getParkingTimeOfVehicle(Vehicle vehicle) {
        return this.slots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTimeOfParking();
    }

    public List<String> getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor(String vehicleName, String color) {
        List<String> listOfDetailsOfBlueColoredToyotaCars = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getColorOfVehicle().equals(color))
                .filter(slot -> slot.getVehicle().getNameOfVehicle().equals(vehicleName))
                .map(slot -> ("ParkingSlot Number is " + slot.getParkingSlotNumber() + "Number Plate " + slot.vehicle.getNumberPlateOfVehicle()))
                .collect(Collectors.toList());
        return listOfDetailsOfBlueColoredToyotaCars;
    }

    public List<String> getDetailsOfCarsInParticularLotByFieldOfVehicle(String fieldOfVehicle) {
        List<String> listOfCarsByFieldOfVehicle = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getNameOfVehicle1(fieldOfVehicle).equals(fieldOfVehicle))
                .map(slot -> ("ParkingSlot Number is " + slot.getParkingSlotNumber() + ", Number Plate " + slot.vehicle.getNumberPlateOfVehicle() + ", Vehicle Name " + slot.vehicle.getNameOfVehicle() + ", Color " + slot.vehicle.getColorOfVehicle()))
                .collect(Collectors.toList());
        return listOfCarsByFieldOfVehicle;
    }

    public List<Vehicle> getListOfAllCarsParkedInLastThirtyMinutesInParticularLot() {
        List<Vehicle> listOfAllCArsParkedInThirtyMinutesInParticularLot = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .filter(slot -> (LocalDateTime.now().getMinute() - slot.getTimeOfParking().getMinute() >= 0 && LocalDateTime.now().getMinute() - slot.getTimeOfParking().getMinute() <= 30))
                .map(slot -> slot.getVehicle()).collect(Collectors.toList());
        return listOfAllCArsParkedInThirtyMinutesInParticularLot;
    }

    public List<String> getDetailsOfAllCarsInParticularLot() {
        List<String> listOfDetailsOfAllCars = this.slots.stream()
                .filter(slot -> slot.getVehicle() != null)
                .map(slot -> ("ParkingSlot Number is " + slot.getParkingSlotNumber() + ", Number Plate " + slot.vehicle.getNumberPlateOfVehicle() + ", Vehicle Name " + slot.vehicle.getNameOfVehicle() + ", Color " + slot.vehicle.getColorOfVehicle() + ", Driver Type " + slot.vehicle.getDriverTypeOfVehicle()))
                .collect(Collectors.toList());
        return listOfDetailsOfAllCars;
    }
}
