package com.majeur.projet.movement;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.emergencyManager.EmergencyManagerFunctions;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.VehicleState;

import java.util.Map;

public class MoveFunctions {

    public static boolean doesFireExist(MissionEntity mission, Map<Integer, FireObject> fireMap){
        return fireMap.containsKey(mission.getDestinationId());
//        try{
//            FireObject fire = StaticGet.getFire(String.valueOf(mission.getDestinationId()));
//            if(fire == null){
//                // Le feu n'existe plus (Réponse requête vide mais pas de 404)
//                System.out.println("feu n'ex plus (2)");
//                return false;
//            }else{
//                // Le feu existe toujours
//                return true;
//            }
//        }catch(HttpServerErrorException e){
//            // Le feu n'existe plus
//            System.out.println("feu n'ex plus (1)");
//            return false;
//        }
    }

    public static double[] getDestinationCoords(MissionEntity mission, FacilityObject facility, Map<Integer,FireObject> fireMap) throws IllegalAccessException {
        double lat;
        double lon;
        if(mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){
            lat = facility.getLat();
            lon = facility.getLon();
            //TODO Déplacer qd on se débarasse de la téléportation
            mission.setVehicleState(VehicleState.AT_FACILITY);

        } else if (mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)){
            FireObject fire = fireMap.get(mission.getDestinationId());
            lat = fire.getLat();
            lon = fire.getLon();
            //TODO Déplacer qd on se débarasse de la téléportation
            mission.setVehicleState(VehicleState.AT_FIRE);
        }else{
            throw new IllegalAccessException();
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

    public static double[] getDestination_Linear(VehicleObject vehicle, MissionEntity mission, double[] destCoords){

        double step = mission.getStep();
        double dirCoeff = (vehicle.getLat()-destCoords[0])/(vehicle.getLon()-destCoords[1]);
        double stepX = step/Math.sqrt(1+dirCoeff);
        double stepY = dirCoeff*stepX;
        double newX = stepX + vehicle.getLon();
        double newY = stepY+vehicle.getLat();
        return new double[]{Math.round(100.0d*newX)/100.0d, Math.round(100.0d*newY)/100.0d};
    }

    public static boolean isVehicleAtDestination(double[] destCoords, double[] vehicleCoords, double step){
        double distance = Math.sqrt(Math.pow(destCoords[0]-vehicleCoords[0],2)+Math.pow(destCoords[1]-vehicleCoords[1],2));
        return distance < step;
    }

    public static VehicleState setVehicleAtDestination(VehicleState state) {
        if(state.equals(VehicleState.GOING_TO_FACILITY)){
            return VehicleState.AT_FACILITY;
        }else if(state.equals(VehicleState.GOING_TO_FIRE)){
            return VehicleState.AT_FIRE;
        }else{
            return state;
        }
    }
}
