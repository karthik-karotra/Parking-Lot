package com.parkinglot;

import com.parkinglotexception.ParkingLotException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LargeVehicle implements ParkingStrategy {
    private List<Slots> slots;

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLot) {
        List<ParkingLot> temporaryLot = new ArrayList<>(parkingLot);
        temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
        return temporaryLot.get(0);
    }
}
