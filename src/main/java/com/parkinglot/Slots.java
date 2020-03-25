package com.parkinglot;

import java.time.LocalDateTime;

public class Slots {

    private Object vehicle;
    private LocalDateTime time;
    private int slotNumber;

    public Slots(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public LocalDateTime getTimeOfParking() {
        return time;
    }

    public void setParkingTimeOfVehicle(Object vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }

    public Object getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
