package com.parkinglot;

import java.time.LocalDateTime;

public class Slots {

    public Vehicle vehicle;
    private LocalDateTime time;
    private int slotNumber;

    public Slots(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public LocalDateTime getTimeOfParking() {
        return time;
    }

    public void setParkingTimeOfVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }
}
