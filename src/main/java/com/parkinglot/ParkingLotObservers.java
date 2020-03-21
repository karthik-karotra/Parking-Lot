package com.parkinglot;

public interface ParkingLotObservers {

    void slotsFull();
    void slotsEmpty();
    boolean checkIfSlotIsFull();
}
