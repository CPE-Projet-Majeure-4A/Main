package com.majeur.projet.emergencyManager;


import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.VehicleState;

import java.util.*;

/**
 * Sends the vehicle to destination
 * sendVehicleToFire(vehicleId, fireId)
 * sendVehicleToFacility(vehicleId)
 * (Eventually, add sendVehicleToRefuel method)
 * Each vehicle available finds the appropriate fire to take in charge
 * If there is no fire, go to nearest facility (refuel?)
 * Methods:
 * assignVehicles()
 * findDestination(vehicleId, listFire)
 */
public class EmergencyManagerFunctions {
	ArrayList<FireObject> listFire = new ArrayList<FireObject>();
	// ArrayList<VehicleObject> listVehicle = new ArrayList<VehicleObject>();
	
	
	public static double calculDistance(double lat1, double lat2, double lon1,
	        double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c;

	    return distance;
	}
	/*
	 * Method : GetFireInArea , require all fires in a list and facility coord
	 * check distance and if distance greater than 50 delete fire from list
	 * return listFire
	 */
	public static Map<Integer, FireObject> GetFireInArea(List<FireObject> listFire, FacilityObject F) {
		Iterator<FireObject> iteratorFires = listFire.iterator();
		Map<Integer, FireObject> firesInArea = new HashMap<>();
		while (iteratorFires.hasNext()) {
			FireObject currentFire = iteratorFires.next();
			double Distance = calculDistance(currentFire.getLat(), F.getLat(), currentFire.getLon(), F.getLon());
			if (Distance < 50) {
				firesInArea.put(currentFire.getId(), currentFire);
			}
		}
		return firesInArea;
	}
		
	/*
	* Called for every vehicle
	* Method : SelectVehicle , require listFire and a vehicle
	* launches WeightFunction -> return fireId (the one with the highest weight)
	* launches sendVehicleToFire(vehicleId, fireId) or sendVehicleToFacility(vehicleId)
	* return void
	*/
	public static MissionEntity SelectVehicle(Map<Integer, FireObject> mapFire, int vehicleId, int facilityId, VehicleObject V) {
		int fireId = WeightFunction(mapFire, V);
		MissionEntity mission = new MissionEntity(vehicleId, 0, null, 0);
		if (fireId == -1) {
			mission.setVehicleState(VehicleState.GOING_TO_FACILITY);
			mission.setDestinationId(facilityId);
		} else {
			mission.setVehicleState(VehicleState.GOING_TO_FIRE);
			mission.setDestinationId(fireId);
		}
		return mission;
	}

		
	/*
	* Method : WeightFunction(listFire, vehicle)
	* Distance vehicle-fire
	* Agent vs Fire Type
	* Fire range and intensity vs vehicle Capacity
	* Find the highest weight
	* return fireId (from the fire with highest weight)
	*/
	// if fireId = -1 alors on a pas de feu pris en charge par le véhicule
	public static int WeightFunction(Map<Integer, FireObject> mapFire, VehicleObject V) {
		int fireId = -1;
		for(FireObject currentFire : mapFire.values()) {
			double Weight;
			double MaxWeight = 0;
			double Distance = calculDistance(currentFire.getLat(), V.getLat(), currentFire.getLon(), V.getLon());
			Weight = -Math.sqrt(8100*Distance) + 900;

			Weight += 100; // On utilise ALL pour l'instant donc 1.0f en efficacité
			
			float firePower = currentFire.getIntensity();
			float vehiclePower = V.getLiquidQuantity() * 1f * 0.1f; // ALL = 1 efficacité et 0.1 atténuation feu
			if (firePower > vehiclePower) {
				Weight -= (firePower - vehiclePower) * 10;
				if (Weight < 0) {
					Weight = 0;
				}
			}
			if (MaxWeight < Weight) {
				MaxWeight = Weight;
				if (MaxWeight >= 10) {
					fireId = currentFire.getId();
				}
				else {
					fireId = -1;
				}
			}
		}
		return fireId;
	}
	public static double ComputeStep(VehicleObject vehicle, int fireId, FacilityObject facility, VehicleState state) {
		// Step = number of steps left to reach destination
		double steps = 5;
		/*
		if(state.equals(VehicleState.GOING_TO_FACILITY)) {
			double distance = Math.sqrt(Math.pow(facility.getLon()-vehicle.getLon(), 2)+Math.pow(facility.getLat()-vehicle.getLat(), 2));
			int wayPoint = (int) Math.max(Math.round(distance/2), 5);
			steps = distance / wayPoint;
		}
		else if(state.equals(VehicleState.GOING_TO_FIRE)) {
			FireObject fire = StaticGet.getFire(Integer.toString(fireId));
			double distance = Math.sqrt(Math.pow(fire.getLon()-vehicle.getLon(), 2)+Math.pow(fire.getLat()-vehicle.getLat(), 2));
			int wayPoint = (int) Math.max(Math.round(distance/2), 5);
			steps = distance / wayPoint;
		}
		*/
		return steps;
	}
}
