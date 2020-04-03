package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import java.util.List;

public class HandicapDriver implements ParkingStrategy {

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLot) {
        ParkingLot parkingLots = parkingLot.stream()
                .filter(lot -> lot.getAvailableEmptySlots().size() > 0)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("Lots full", ParkingLotException.ExceptionType.LOTS_FULL));
        return parkingLots;
    }
}
