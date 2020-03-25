package com.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class LotManagementSystem {
    private final int totalParkingSlots;
    private final int totalParkingLots;
    public List<ParkingLotSystem> parkingLot;

    public LotManagementSystem(int totalParkingSlots, int totalParkingLots) {
        this.totalParkingSlots = totalParkingSlots;
        this.totalParkingLots = totalParkingLots;
        this.parkingLot = new ArrayList<>();
        IntStream.range(0, totalParkingLots)
                .forEach(assignedLotNo -> parkingLot.add(new ParkingLotSystem(2)));
    }

    public void parkVehicle(Object vehicle) {
        ParkingLotSystem parkingLot = getLot();
        parkingLot.parkVehicle(vehicle);
    }

    private ParkingLotSystem getLot() {
        List<ParkingLotSystem> temporaryLot = new ArrayList<>(this.parkingLot);
        temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
        return temporaryLot.get(0);
    }

    public ParkingLotSystem getLotOfParkedVehicle(Object vehicle) {
        ParkingLotSystem lotOfParkedVehicle = this.parkingLot.stream()
                .filter(lot -> lot.checkIfVehicleIsParked(vehicle))
                .findFirst()
                .orElse(null);
        return lotOfParkedVehicle;
    }
}
