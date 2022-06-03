package com.majeur.projet.threading;

public class MissionObject {
    private int vehicleId;
    private int destinationId;
    private VehicleState vehicleState;

    public MissionObject(int vehicleId, int destinationId, VehicleState vehicleState) {
        this.vehicleId = vehicleId;
        this.destinationId = destinationId;
        this.vehicleState = vehicleState;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public VehicleState getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(VehicleState vehicleState) {
        this.vehicleState = vehicleState;
    }
}
