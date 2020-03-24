package com.parkinglotexception;

public class ParkingLotException extends RuntimeException {
    public enum ExceptionType {
        VEHICLE_NOT_PARKED, VEHICLE_NOT_UNPARKED, SLOT_FULL, VEHICLE_NOT_FOUND;
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
