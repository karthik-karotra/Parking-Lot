package com.parkinglot;

public class ParkingLotSystem {

    private Object vehicleType;

    public void parkVehicle(Object vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean checkIfVehicleIsParked() {
        if (this.vehicleType != null)
            return true;
        return false;
    }

    public void unparkVehicle(Object vehicleType) {
        if (this.vehicleType.equals(vehicleType)) {
            this.vehicleType = null;
        }
    }

    public boolean checkIfVehicleIsUnParked() {
        if (this.vehicleType == null)
            return true;
        return false;
    }
}
