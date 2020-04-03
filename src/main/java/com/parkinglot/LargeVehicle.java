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
        /*Predicate<ParkingLot> areThereThreeEmptySlots = lot -> lot.getAvailableEmptySlots().size() > 2;
        Predicate<ParkingLot> areThereAnyEmptySlots = lot -> lot.getAvailableEmptySlots().size() > 0;

        ParkingLot parkingLots = parkingLot.stream()
                .filter(areThereThreeEmptySlots.and(areThereAnyEmptySlots))
                .findFirst()
                .orElse(null);*/
        List<ParkingLot> temporaryLot = new ArrayList<>(parkingLot);
        temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
        return temporaryLot.get(0);

      /*  ParkingLot parkingLots = parkingLot.stream()
                .filter(lot -> {if (lot.getAvailableEmptySlots().size()>2) {
                    return lot;
                } else if (lot.getAvailableEmptySlots().size()>0) return lot;
                else throw new ParkingLotException("Lots full", ParkingLotException.ExceptionType.LOTS_FULL);
                })
               .findFirst()
                .orElse(null);*/

    }
}
