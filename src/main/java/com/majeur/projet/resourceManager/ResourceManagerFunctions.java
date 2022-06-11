package com.majeur.projet.resourceManager;

import com.majeur.projet.apiCommunication.VehicleObject;

public class ResourceManagerFunctions {

    public static void increaseFuel(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getFuel()+5);
    }
    public static void increaseLiquid(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getLiquidQuantity()+5);
    }
    public static void decreaseFuel(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getFuel()-1);
    }
    public static void decreaseLiquid(VehicleObject vehicle){
        vehicle.setFuel(vehicle.getLiquidQuantity()-1);
    }
}
