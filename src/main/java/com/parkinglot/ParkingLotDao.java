package com.parkinglot;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLotDao {
    void initializingSlots();
    boolean parkVehicle(Enum strategyType,Vehicle vehicle);
    void parkVehicle(Integer slot, Vehicle vehicle);
    boolean checkIfVehicleIsParked(Vehicle vehicle);
    void unparkVehicle(Vehicle vehicle);
    boolean checkIfVehicleIsUnParked(Vehicle vehicle);
    void registerObserver(ParkingLotObservers observer);
    void setTotalSlotCapacity(int totalSlotCapacity);
    List<Integer> getAvailableEmptySlots();
    Integer findVehicle(Vehicle vehicle);
    Integer getCountOfVehiclesParked();
    LocalDateTime getParkingTimeOfVehicle(Vehicle vehicle);
    List<Integer> getListOfWhiteVehiclesInParticularLotByColor(String color);
    List<String> getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor(String vehicleName,String color);
    List<String> getListOfBMWCarsInParticularLotByVehicleName(String vehicleName);
    List<Vehicle> getListOfAllCarsParkedInLastThirtyMinutesInParticularLot();
    List<String> getDetailsOfHandicapeDriverVehicles(String driverType);
}
