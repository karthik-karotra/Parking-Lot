package com.parkinglot;

import java.util.List;

public interface ParkingLotSystemDao {
    void parkVehicle(Enum strategyType, Vehicle vehicle);
    ParkingLot getLotOfParkedVehicle(Vehicle vehicle);
    List<List<Integer>> getOverallLotOfWhiteColorVehicle(String color);
}
