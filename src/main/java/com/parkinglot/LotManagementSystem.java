package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LotManagementSystem {
    private final int totalParkingSlots;
    private final int totalParkingLots;
    public List<ParkingLotSystem> parkingLot;
    private StrategyTypeFactory strategyTypeFactory;

    public LotManagementSystem(int totalParkingSlots, int totalParkingLots) {
        this.totalParkingSlots = totalParkingSlots;
        this.totalParkingLots = totalParkingLots;
        this.parkingLot = new ArrayList<>();
        this.strategyTypeFactory = new StrategyTypeFactory();
        IntStream.range(0, totalParkingLots)
                .forEach(assignedLotNo -> parkingLot.add(new ParkingLotSystem(2)));
    }

    public void parkVehicle(DriverType driverType, Object vehicle) {
        ParkingLotSystem lot = new StrategyTypeFactory().getParkingStrategy(driverType).getLot(this.parkingLot);
        lot.parkVehicle(vehicle);
    }

    public ParkingLotSystem getLotOfParkedVehicle(Object vehicle) {
        ParkingLotSystem lotOfParkedVehicle = this.parkingLot.stream()
                .filter(lot -> lot.checkIfVehicleIsParked(vehicle))
                .findFirst()
                .orElse(null);
        return lotOfParkedVehicle;
    }
}
