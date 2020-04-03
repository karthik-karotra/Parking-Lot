package com.parkinglot;

public class Vehicle {
    private String driverType;
    private String color;
    private String vehicleName   ;
    private String numberPlate;

    public Vehicle(String color) {
        this.color=color;
    }

    public Vehicle(String color,String vehicleName,String numberPlate) {
        this.color=color;
        this.vehicleName=vehicleName;
        this.numberPlate=numberPlate;
    }

    public Vehicle(String color,String vehicleName,String numberPlate,String driverType) {
        this.color=color;
        this.vehicleName=vehicleName;
        this.numberPlate=numberPlate;
        this.driverType=driverType;
    }

    public String getNameOfVehicle() {
        return vehicleName;
    }

    public String getNumberPlateOfVehicle() {
        return numberPlate;
    }

    public String getColorOfVehicle() {
        return color;
    }

    public String getDriverTypeOfVehicle() {
        return driverType;
    }

}
