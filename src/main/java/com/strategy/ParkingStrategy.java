package com.strategy;

import com.parkinglot.ParkingLot;
import java.util.List;

public interface ParkingStrategy {
    ParkingLot getLot(List<ParkingLot> parkingLot);
}
