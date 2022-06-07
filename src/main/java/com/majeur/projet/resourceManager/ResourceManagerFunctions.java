package com.majeur.projet.resourceManager;

import com.majeur.projet.apiCommunication.VehicleObject;

public class ResourceManagerFunctions {

    public static VehicleObject increaseFuel(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getFuel()+5);
        return vehicle;
    }
    public static VehicleObject increaseLiquid(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getLiquidQuantity()+5);
        return vehicle;
    }
    public static VehicleObject decreaseFuel(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getFuel()-1);
        return vehicle;
    }
    public static VehicleObject decreaseLiquid(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getLiquidQuantity()-1);
        return vehicle;
    }
}
