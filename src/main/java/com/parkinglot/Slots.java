package com.parkinglot;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slots that = (Slots) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(time, that.time);
    }
}
