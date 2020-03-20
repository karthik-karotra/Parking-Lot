package com.parkinglot;

public class SecurityPerson {
    private boolean slotsFull;

    public void slotsFull() {
        this.slotsFull = true;
    }

    public boolean checkIfSlotIsFull() {
        return slotsFull;
    }
}
