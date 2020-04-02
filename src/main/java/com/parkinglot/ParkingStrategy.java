package com.parkinglot;

import java.util.List;

public interface ParkingStrategy {
    ParkingLot getLot(List<ParkingLot> parkingLot);
}
