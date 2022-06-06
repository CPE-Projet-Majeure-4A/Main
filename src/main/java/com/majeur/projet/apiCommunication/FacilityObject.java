package com.majeur.projet.apiCommunication;

public class FacilityObject {
    private int id;
    private double lat;
    private double lon;
    private int maxVehicleSpace;
    private int peopleCapacity;
    private String teamUuid;
    private int[] peopleIdSet;
    private int[] vehicleIdSet;

    public FacilityObject(){
    	
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
    public double getLon() {
        return lon;
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