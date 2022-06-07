package com.majeur.projet.movement;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.VehicleState;
import org.springframework.web.client.HttpServerErrorException;

public class MoveFunctions {

    public static boolean doesFireExist(MissionEntity mission){
        //TODO Faire sans spammer l'api
        try{
            FireObject fire = StaticGet.getFire(String.valueOf(mission.getDestinationId()));
            // Le feu existe toujours
            return true;
        }catch(HttpServerErrorException e){
            // Le feu n'existe plus
            return false;
        }
    }

    public static double[] getDestinationCoords(MissionEntity mission, FacilityObject facility){
        double lat;
        double lon;
        if(mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){
            lat = facility.getLat();
            lon = facility.getLon();
            //TODO Déplacer qd on se débarasse de la téléportation
            mission.setVehicleState(VehicleState.AT_FACILITY);

        } else {
            //TODO Faire propre sans spammer l'api
            FireObject fire = StaticGet.getFire(String.valueOf(mission.getDestinationId()));
            lat = fire.getLat();
            lon = fire.getLon();
            //TODO Déplacer qd on se débarasse de la téléportation
            mission.setVehicleState(VehicleState.AT_FIRE);
        }
        return new double[]{lat, lon};
    }

    public static boolean isVehicleMoving(MissionEntity mission){
        return mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY) ||
                mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE);
    }

    public static double[] getDestination_Teleportation(double[] destCoords){
        return destCoords;
    }

    public static double[] getDestination_Linear(MissionEntity mission, double[] destCoords){
        //TODO
        return null;
    }
}
