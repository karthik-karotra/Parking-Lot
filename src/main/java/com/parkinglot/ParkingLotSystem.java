package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem implements ParkingLotSystemDao {
    private final int totalParkingSlots;
    private final int totalParkingLots;
    public List<ParkingLot> parkingLot;
    private StrategyTypeFactory strategyTypeFactory;

    public ParkingLotSystem(int totalParkingSlots, int totalParkingLots) {
        this.totalParkingSlots = totalParkingSlots;
        this.totalParkingLots = totalParkingLots;
        this.parkingLot = new ArrayList<>();
        this.strategyTypeFactory = new StrategyTypeFactory();
        IntStream.range(0, totalParkingLots)
                .forEach(assignedLotNo -> parkingLot.add(new ParkingLot(5)));
    }

    @Override
    public void parkVehicle(Enum strategyType, Object vehicle) {
        ParkingLot lot = new StrategyTypeFactory().getParkingStrategy(strategyType).getLot(this.parkingLot);
        lot.parkVehicle(strategyType,vehicle);
    }

    @Override
    public ParkingLot getLotOfParkedVehicle(Object vehicle) {
        ParkingLot lotOfParkedVehicle = this.parkingLot.stream()
                .filter(lot -> lot.checkIfVehicleIsParked(vehicle))
                .findFirst()
                .orElse(null);
        return lotOfParkedVehicle;
    }
}
