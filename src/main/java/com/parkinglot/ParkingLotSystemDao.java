package com.parkinglot;

public interface ParkingLotSystemDao {

    void parkVehicle(DriverType driverType, Object vehicle);
    void parkVehicle(VehicleType vehicleType, Object vehicle);
    ParkingLot getLotOfParkedVehicle(Object vehicle);
}
