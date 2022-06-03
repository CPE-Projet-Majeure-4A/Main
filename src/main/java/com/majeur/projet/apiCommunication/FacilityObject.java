package com.majeur.projet.apiCommunication;

public class FacilityObject {

    private int crewMember;
    private int facilityRefID;
    private float fuel;
    private int id;
    private float lat;
    private float lon;
    private float liquidQuantity;
    private String liquidType;
    private String type;

    public FacilityObject(){
    }

    public int getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(int crewMember) {
        this.crewMember = crewMember;
    }

    public int getFacilityRefID() {
        return facilityRefID;
    }

    public void setFacilityRefID(int facilityRefID) {
        this.facilityRefID = facilityRefID;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLiquidQuantity() {
        return liquidQuantity;
    }

    public void setLiquidQuantity(float liquidQuantity) {
        this.liquidQuantity = liquidQuantity;
    }

    public String getLiquidType() {
        return liquidType;
    }

    public void setLiquidType(String liquidType) {
        this.liquidType = liquidType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
