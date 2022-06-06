package com.majeur.projet.threading;

import javax.persistence.*;

@Entity
public class MissionEntity {
    @Id
    @GeneratedValue
    private int missionId;

    private int vehicleId;
    private int destinationId;
    private VehicleState vehicleState;

    public MissionEntity(int vehicleId, int destinationId, VehicleState vehicleState) {
        this.vehicleId = vehicleId;
        this.destinationId = destinationId;
        this.vehicleState = vehicleState;
    }

    public MissionEntity() {

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

    @Override
    public String toString() {
        return "MissionObject{" +
                "vehicleId=" + vehicleId +
                ", destinationId=" + destinationId +
                ", vehicleState=" + vehicleState +
                '}';
    }
}
