package com.majeur.projet.Updater.Vehicle;

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
	listFire = Fire.getAllFire();
	
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

	    re//-----------------------Calcul Distance -----------------------
	    /**
	     * Calculate distance between two points in latitude and longitude taking
	     * into account height difference. If you are not interested in height
	     * difference pass 0.0. Uses Haversine method as its base.
	     * 
	     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	     * el2 End altitude in meters
	     * @returns Distance in Meters
	     */
	    /*
	    public static double distance(double lat1, double lat2, double lon1,
	            double lon2, double el1, double el2) {

	        final int R = 6371; // Radius of the earth

	        double latDistance = Math.toRadians(lat2 - lat1);
	        double lonDistance = Math.toRadians(lon2 - lon1);
	        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        double distance = R * c * 1000; // convert to meters

	        double height = el1 - el2;

	        distance = Math.pow(distance, 2) + Math.pow(height, 2);

	        return Math.sqrt(distance);
	    }
	    */
	    //--------------------------------------------------------------------------turn distance;
	}
	/*
	 * Method : GetFireInArea , require all fires in a list and facility coord
	 * check distance and if distance greater than 50 delete fire from list
	 * return listFire
	 */
	public static void GetFireInArea(List listFire, Facility F) {
		new Iterator<>
		foreach.listFire {
			double Distance = calculDistance(currentFire  .lat, F.lat, currentFire.lon, F.lon);
			if (Distance < 50) {
				listFire.pop()
			}
		}
	}
		
	/*
	* Called for every vehicle
	* Method : SelectVehicle , require listFire and a vehicle
	* launches WeightFunction -> return fireId (the one with the highest weight)
	* launches sendVehicleToFire(vehicleId, fireId) or sendVehicleToFacility(vehicleId)
	* return void
	*/
	public SelectVehicle(List listFire, Vehicle V) {
		new fireId = WeightFunction(listFire, V);
		if (fireId = -1) {
			sendVehicleToFacility(V.id);
		}
		else {
			sendVehicleToFire(V.id, fireId);
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
	public int WeightFunction(List listFire, Vehicle V) {
		Iterator <listFire> 
		foreach.listFire {
			new FireObject currentFire = Fire;
			int fireId = -1;
			float Weight;
			float MaxWeight = 0;
			DistanceLon = abs(currentFire.lon - V.lon);
			DistanceLat = abs(currentFire.lat - V.lat);
			// calculDistance pas fait encore
			Distance = calculDistance(DistanceLon, DistanceLat);
			Weight = -sqrt(8100*Distance) + 900;
			
			Type = getFireType(currentFire);
			agentType = getAgentType(V);
			Weight += agentTypeEfficacity * 100;
			
			fireSize = currentFire.range * currentFire.intensity;
			vehicleSize = V.liquidQuantity * agentTypeEfficacity;
			if (fireSize > vehicleSize) {
				Weight -= 500;
				if (Weight < 0) {
					Weight = 0;
				}
			}
			if (MaxWeight < Weight) {
				MaxWeight = Weight;
				fireId = currentFire.id;
			}
			return fireId;
		}
	}
}
