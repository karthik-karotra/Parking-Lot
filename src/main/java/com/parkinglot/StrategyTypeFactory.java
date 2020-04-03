package com.parkinglot;

public class StrategyTypeFactory {

    public ParkingStrategy getParkingStrategy(Enum strategyType) {
        if (strategyType.equals(DriverType.NORMAL_DRIVER))
            return new NormalDriver();
        else if (strategyType.equals(VehicleType.LARGE_VEHICLE))
            return new LargeVehicle();
        return new HandicapDriver();
    }
}
