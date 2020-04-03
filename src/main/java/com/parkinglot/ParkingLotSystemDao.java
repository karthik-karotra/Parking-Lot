package com.parkinglot;

import java.util.List;

public interface ParkingLotSystemDao {
    void parkVehicle(Enum strategyType, Vehicle vehicle);
    ParkingLot getLotOfParkedVehicle(Vehicle vehicle);
    List<List<Integer>> getOverallLotOfWhiteColorVehicle(String color);
    List<List<String>> getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor(String vehicleName,String color);
    List<List<String>> getOverallListOfBMWCarsInDfferentLots(String vehicleName);
    List<List<Vehicle>> getListOfAllCarsParkedInLastThirtyMinutesInAllLots();
    List<List<String>> getDetailsOfHandicapeDriverVehiclesInAllLots(String driverType);
}
