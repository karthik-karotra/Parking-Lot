package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem implements ParkingLotSystemDao {
    private final int totalParkingSlots;
    private final int totalParkingLots;
    public List<ParkingLot> parkingLot;
    private StrategyTypeFactory strategyTypeFactory;

    public ParkingLotSystem(int totalParkingSlots, int totalParkingLots) {
        this.totalParkingSlots = totalParkingSlots;
        this.totalParkingLots = totalParkingLots;
        this.parkingLot = new ArrayList();
        this.strategyTypeFactory = new StrategyTypeFactory();
        IntStream.range(0, totalParkingLots)
                .forEach(assignedLotNo -> parkingLot.add(new ParkingLot(5)));
    }

    @Override
    public void parkVehicle(Enum strategyType, Vehicle vehicle) {
        ParkingLot lot = new StrategyTypeFactory().getParkingStrategy(strategyType).getLot(this.parkingLot);
        lot.parkVehicle(strategyType, vehicle);
    }

    @Override
    public ParkingLot getLotOfParkedVehicle(Vehicle vehicle) {
        ParkingLot lotOfParkedVehicle = this.parkingLot.stream()
                .filter(lot -> lot.checkIfVehicleIsParked(vehicle))
                .findFirst()
                .orElse(null);
        return lotOfParkedVehicle;
    }

    @Override
    public List<List<Integer>> getOverallLotOfWhiteColorVehicle(String color) {
        List<List<Integer>> overallListOfWhiteVehicles = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getListOfWhiteVehiclesInParticularLotByColor(color)).collect(Collectors.toList());
        return overallListOfWhiteVehicles;
    }

    @Override
    public List<List<String>> getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor(String vehicleName, String color) {
        List<List<String>> overallListOfBlueColoredToyotaCarsInDifferentLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor(vehicleName, color)).collect(Collectors.toList());
        return overallListOfBlueColoredToyotaCarsInDifferentLots;
    }

    @Override
    public List<List<String>> getOverallListOfBMWCarsInDfferentLots(String vehicleName) {
        List<List<String>> overallListOfBMWCars = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getListOfBMWCarsInParticularLotByVehicleName(vehicleName)).collect(Collectors.toList());
        return overallListOfBMWCars;
    }

    @Override
    public List<List<Vehicle>> getListOfAllCarsParkedInLastThirtyMinutesInAllLots() {
        List<List<Vehicle>> listOfAllCarsParkedInLastThirtyMinutesInAllLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getListOfAllCarsParkedInLastThirtyMinutesInParticularLot())
                .collect(Collectors.toList());
        return listOfAllCarsParkedInLastThirtyMinutesInAllLots;
    }

    @Override
    public List<List<String>> getDetailsOfHandicapeDriverVehiclesInAllLots(String driverType) {
        List<List<String>> overallListOfHandicapeDriverVehiclesInAllLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfHandicapeDriverVehicles(driverType)).collect(Collectors.toList());
        return overallListOfHandicapeDriverVehiclesInAllLots;
    }

    @Override
    public List<List<String>> getDetailsOfAllVehiclesInAllLots() {
        List<List<String>> overallListOfAllVehiclesInAllLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfAllCarsInParticularLot()).collect(Collectors.toList());
        return overallListOfAllVehiclesInAllLots;
    }
}
