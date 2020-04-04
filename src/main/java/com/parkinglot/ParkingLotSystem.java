package com.parkinglot;

import com.strategy.StrategyTypeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {
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

    public void parkVehicle(Enum strategyType, Vehicle vehicle) {
        ParkingLot lot = new StrategyTypeFactory().getParkingStrategy(strategyType).getLot(this.parkingLot);
        lot.parkVehicle(strategyType, vehicle);
    }

    public ParkingLot getLotOfParkedVehicle(Vehicle vehicle) {
        ParkingLot lotOfParkedVehicle = this.parkingLot.stream().filter(lot -> lot.checkIfVehicleIsParked(vehicle)).findFirst()
                .orElse(null);
        return lotOfParkedVehicle;
    }

    public List<List<String>> getDetailsOfAllBlueToyotaCarsDifferentLotsByNameAndColor(String vehicleName, String color) {
        List<List<String>> overallListOfBlueColoredToyotaCarsInDifferentLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfBlueToyotaCarsInParticularLotByNameAndColor(vehicleName, color)).collect(Collectors.toList());
        return overallListOfBlueColoredToyotaCarsInDifferentLots;
    }

    public List<List<String>> getOverallListOfCarsInDifferentLotsByFieldOfVehicle(String fieldOfVehicle) {
        List<List<String>> overallListOfCarsByFieldOfVehicle = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfCarsInParticularLotByFieldOfVehicle(fieldOfVehicle)).collect(Collectors.toList());
        return overallListOfCarsByFieldOfVehicle;
    }

    public List<List<Vehicle>> getListOfAllCarsParkedInLastThirtyMinutesInAllLots() {
        List<List<Vehicle>> listOfAllCarsParkedInLastThirtyMinutesInAllLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getListOfAllCarsParkedInLastThirtyMinutesInParticularLot())
                .collect(Collectors.toList());
        return listOfAllCarsParkedInLastThirtyMinutesInAllLots;
    }

    public List<List<String>> getDetailsOfAllVehiclesInAllLots() {
        List<List<String>> overallListOfAllVehiclesInAllLots = this.parkingLot.stream()
                .map(parkingLot -> parkingLot.getDetailsOfAllCarsInParticularLot()).collect(Collectors.toList());
        return overallListOfAllVehiclesInAllLots;
    }
}
