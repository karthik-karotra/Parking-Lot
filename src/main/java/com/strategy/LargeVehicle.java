package com.strategy;

import com.parkinglot.ParkingLot;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LargeVehicle implements ParkingStrategy {
    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLot) {
        List<ParkingLot> temporaryLot = new ArrayList(parkingLot);
        temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
        return temporaryLot.get(0);
    }
}
