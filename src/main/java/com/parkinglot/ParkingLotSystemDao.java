package com.parkinglot;

public interface ParkingLotSystemDao {

    void parkVehicle(Enum strategyType, Object vehicle);
    ParkingLot getLotOfParkedVehicle(Object vehicle);
}
