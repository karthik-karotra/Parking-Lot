package com.parkinglot;

import com.parkinglotexception.ParkingLotException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum DriverType {
    HANDICAPE_DRIVER {
        @Override
        public ParkingLotSystem getLot(List<ParkingLotSystem> parkingLot) {
            ParkingLotSystem parkingLots = parkingLot.stream()
                    .filter(lot -> lot.getAvailableEmptySlots().size() > 0)
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("Lots full", ParkingLotException.ExceptionType.LOTS_FULL));
            return parkingLots;
        }
    },
    NORMAL_DRIVER {
        @Override
        public ParkingLotSystem getLot(List<ParkingLotSystem> parkingLot) {
            List<ParkingLotSystem> temporaryLot = new ArrayList<>(parkingLot);
            temporaryLot.sort(Comparator.comparing(lot -> lot.getCountOfVehiclesParked()));
            return temporaryLot.get(0);
        }
    };

    public abstract ParkingLotSystem getLot(List<ParkingLotSystem> parkingLot);
}
