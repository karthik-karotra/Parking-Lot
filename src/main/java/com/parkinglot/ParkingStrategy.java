package com.parkinglot;

import java.util.List;

public interface ParkingStrategy {
    ParkingLotSystem getLot(List<ParkingLotSystem> parkingLotSystem);
}
