package com.parkinglot;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLotDao {

    void initializingSlots();
    boolean parkVehicle(Enum strategyType,Object vehicle);
    void parkVehicle(Integer slot, Object vehicle);
    boolean checkIfVehicleIsParked(Object vehicle);
    void unparkVehicle(Object vehicle);
    boolean checkIfVehicleIsUnParked(Object vehicle);
    void registerObserver(ParkingLotObservers observer);
    void setTotalSlotCapacity(int totalSlotCapacity);
    List<Integer> getAvailableEmptySlots();
    Integer findVehicle(Object vehicle);
    Integer getCountOfVehiclesParked();
    LocalDateTime getParkingTimeOfVehicle(Object vehicle);
}
