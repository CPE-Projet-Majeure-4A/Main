package com.majeur.projet.ApiCommunication;

public class FacilityObject {
    /**
     *     "crewMember": 0,
     *     "facilityRefID": 0,
     *     "fuel": 0,
     *     "id": 0,
     *     "lat": 0,
     *     "liquidQuantity": 0,
     *     "liquidType": "ALL",
     *     "lon": 0,
     *     "type": "CAR"
     */
    private int crewMember;
    private int facilityRefID;
    private float fuel;
    private int id;
    private float lat;
    private float lon;
    private float liquidQuantity;
    private String liquidType;
    private String type;

    public FacilityObject(int crewMember, int facilityRefID, float fuel, int id, float lat, float lon, float liquidQuantity, String liquidType, String type) {
        this.crewMember = crewMember;
        this.facilityRefID = facilityRefID;
        this.fuel = fuel;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.liquidQuantity = liquidQuantity;
        this.liquidType = liquidType;
        this.type = type;
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
