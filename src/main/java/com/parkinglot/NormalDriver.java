package com.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NormalDriver implements ParkingStrategy {

    @Override
    public ParkingLotSystem getLot(List<ParkingLotSystem> parkingLot) {
        List<ParkingLotSystem> temporaryLot = new ArrayList<>(parkingLot);
        temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
        return temporaryLot.get(0);
    }
}
