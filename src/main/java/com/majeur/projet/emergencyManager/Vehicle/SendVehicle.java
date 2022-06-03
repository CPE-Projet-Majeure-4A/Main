package com.majeur.projet.emergencyManager.Vehicle;

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
  
/*
 * Method : GetFireInArea , require all fires in a list and facility coord
 * check distance and if distance greater than 50 delete fire from list
 * return listFire
 */
	
/*
* Called for every vehicle
* Method : SelectVehicle , require listFire and a vehicle
* launches WeightFunction -> return fireId (the one with the highest weight)
* launches sendVehicleToFire(vehicleId, fireId) or sendVehicleToFacility(vehicleId)
* return void
*/

	
/*
* Method : WeightFunction(listFire, vehicle)
* Distance vehicle-fire
* Agent vs Fire Type
* Fire range and intensity vs vehicle Capacity
* Find the highest weight
* return fireId (from the fire with highest weight)
*/
}
