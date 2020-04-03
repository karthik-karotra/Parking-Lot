package com.parkinglot;

public class StrategyTypeFactory {

    public ParkingStrategy getParkingStrategy(DriverType driverType) {
        if (driverType.equals(DriverType.NORMAL_DRIVER))
            return new NormalDriver();
        return new HandicapDriver();

    }

    public ParkingStrategy getParkingStrategy(VehicleType vehicleType) {
        if (vehicleType.equals(VehicleType.LARGE_VEHICLE))
            return new LargeVehicle();
        return null;
    }

}
