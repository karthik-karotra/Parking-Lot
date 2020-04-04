package com.strategy;

import com.parkinglot.ParkingLot;
import com.parkinglotexception.ParkingLotException;
import java.util.List;

public class HandicapDriver implements ParkingStrategy {
    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLot) {
        ParkingLot parkingLots = parkingLot.stream()
                .filter(lot -> lot.getAvailableEmptyParkingSlots().size() > 0).findFirst()
                .orElseThrow(() -> new ParkingLotException("Lots full", ParkingLotException.ExceptionType.LOTS_FULL));
        return parkingLots;
    }
}
