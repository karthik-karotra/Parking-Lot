package com.parkinglot;

public class ParkingLotSystem {

    private Object vehicleType;

    public void getParked(Object vehicleType) {
        this.vehicleType=vehicleType;
    }

    public boolean checkIfVehicleIsParked() {
        if(this.vehicleType != null)
            return true;
        return false;
    }
}
