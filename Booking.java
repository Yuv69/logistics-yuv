package com.example.firebasetask;

public class Booking {
    private String pickupLocation;
    private String dropOffLocation;
    private String vehicleType;

    public Booking() {
    }

    public Booking(String pickupLocation, String dropOffLocation, String vehicleType) {
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.vehicleType = vehicleType;
    }

    // Getters and setters
    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}

