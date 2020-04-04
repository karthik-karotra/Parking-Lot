package com.observers;

public interface ParkingLotObservers {
    void slotsFull();
    void slotsEmpty();
    boolean checkIfParkingSlotIsFull();
}
