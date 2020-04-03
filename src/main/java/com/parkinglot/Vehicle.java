package com.parkinglot;

public class Vehicle {
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

    public String getNameOfVehicle() {
        return vehicleName;
    }

    public String getNumberPlateOfVehicle() {
        return numberPlate;
    }

    public String getColorOfVehicle() {
        return color;
    }
}
