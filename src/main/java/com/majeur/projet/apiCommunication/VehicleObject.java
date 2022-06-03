package com.majeur.projet.apiCommunication;

public class VehicleObject {
    private int id;
    private int intensity;
    private float lat;
    private float lon;
    private String type;
    private int maxVehicleSpace;
    private int peopleCapacity;
    private String teamUuid;
    private int[] peopleIdSet;
    private int[] vehicleIdSet;

    public VehicleObject(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxVehicleSpace() {
        return maxVehicleSpace;
    }

    public void setMaxVehicleSpace(int maxVehicleSpace) {
        this.maxVehicleSpace = maxVehicleSpace;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(int peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public String getTeamUuid() {
        return teamUuid;
    }

    public void setTeamUuid(String teamUuid) {
        this.teamUuid = teamUuid;
    }

    public int[] getPeopleIdSet() {
        return peopleIdSet;
    }

    public void setPeopleIdSet(int[] peopleIdSet) {
        this.peopleIdSet = peopleIdSet;
    }

    public int[] getVehicleIdSet() {
        return vehicleIdSet;
    }

    public void setVehicleIdSet(int[] vehicleIdSet) {
        this.vehicleIdSet = vehicleIdSet;
    }
}
