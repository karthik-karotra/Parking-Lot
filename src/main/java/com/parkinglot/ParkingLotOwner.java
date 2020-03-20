package com.parkinglot;

public class ParkingLotOwner {
    private boolean slotsFull;

    public void slotsFull() {
        this.slotsFull = true;
    }

    public boolean checkIfSlotIsFull() {
        return slotsFull;
    }
}
