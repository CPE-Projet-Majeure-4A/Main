package com.majeur.projet.emergencyManager.Vehicle;


import com.majeur.projet.ApiCommunication.FireObject;
import com.majeur.projet.ApiCommunication.FacilityObject;
import com.majeur.projet.ApiCommunication.VehicleObject;
import com.majeur.projet.ApiCommunication.StaticGet;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

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
public class SendVehicle {
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
	public static ArrayList GetFireInArea(ArrayList listFire, FacilityObject F) {
		Iterator<FireObject> iteratorFires = listFire.iterator();
		while (iteratorFires.hasNext()  == true) {
			FireObject currentFire = iteratorFires.next();
			double Distance = calculDistance(currentFire.getLat(), F.getLat(), currentFire.getLon(), F.getLon());
			if (Distance < 50) {
				iteratorFires.remove();
			}
		}
		return listFire;
	}
		
	/*
	* Called for every vehicle
	* Method : SelectVehicle , require listFire and a vehicle
	* launches WeightFunction -> return fireId (the one with the highest weight)
	* launches sendVehicleToFire(vehicleId, fireId) or sendVehicleToFacility(vehicleId)
	* return void
	*/
	public static void SelectVehicle(ArrayList listFire, VehicleObject V) {
		int fireId = WeightFunction(listFire, V);
		if (fireId == -1) {
			// sendVehicleToFacility(V.getId());
		}
		else {
			// sendVehicleToFire(V.getId(), fireId);
		}
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
	public static int WeightFunction(ArrayList listFire, VehicleObject V) {
		Iterator<FireObject> iteratorFires = listFire.iterator();
		int fireId = -1;
		while (iteratorFires.hasNext()  == true) {
			FireObject currentFire = iteratorFires.next();
			double Weight;
			double MaxWeight = 0;
			double Distance = calculDistance(currentFire.getLat(), V.getLat(), currentFire.getLon(), V.getLon());
			Weight = -Math.sqrt(8100*Distance) + 900;
			
			/*
			Type = getFireType(currentFire);
			agentType = getAgentType(V);
			Weight += agentTypeEfficacity * 100;
			*/
			Weight += 100; // On utilise ALL pour l'instant donc 1.0f en efficacité
			
			float firePower = currentFire.getIntensity();
			float vehiclePower = V.getLiquidQuantity() * 0.1f; // ALL = 1 efficacité
			if (firePower > vehiclePower) {
				Weight -= 500;
				if (Weight < 0) {
					Weight = 0;
				}
			}
			if (MaxWeight < Weight) {
				MaxWeight = Weight;
				fireId = currentFire.getId();
			}

		}
		return fireId;
	}
}