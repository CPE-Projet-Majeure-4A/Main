package com.majeur.projet.apiCommunication;

public class VehicleObject {
    private int id;
    private double lat;
    private double lon;
    private String liquidType;
    private float liquidQuantity;
    private int crewMember;
    private int facilityRefId;
    private float fuel;
    private String type;

    public VehicleObject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLiquidType() {
        return liquidType;
    }

    public void setLiquidType(String liquidType) {
        this.liquidType = liquidType;
    }

    public float getLiquidQuantity() {
        return liquidQuantity;
    }

    public void setLiquidQuantity(float liquidQuantity) {
        this.liquidQuantity = liquidQuantity;
    }

    public int getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(int crewMember) {
        this.crewMember = crewMember;
    }

    public int getFacilityRefId() {
        return facilityRefId;
    }

    public void setFacilityRefId(int facilityRefId) {
        this.facilityRefId = facilityRefId;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
