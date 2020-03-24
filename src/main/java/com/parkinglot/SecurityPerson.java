package com.parkinglot;

public class SecurityPerson implements ParkingLotObservers {

    private boolean slotsFull;

    @Override
    public void slotsFull() {
        this.slotsFull = true;
    }

    @Override
    public void slotsEmpty() {
        this.slotsFull = false;
    }

    @Override
    public boolean checkIfSlotIsFull() {
        return slotsFull;
    }
}
