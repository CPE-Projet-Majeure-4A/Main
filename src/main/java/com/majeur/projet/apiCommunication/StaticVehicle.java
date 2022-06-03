package com.majeur.projet.apiCommunication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Sends UPDATE, PUT and DELETE requests to Vehicle API
 */
public class StaticVehicle {
    static final private String url = "http://vps.cpe-sn.fr:8081/vehicle";

    @Value("com.majeur.projet.teamUuid")
    private static String teamUuid;

    public static boolean updateVehicle(String vehicleId, Object request, String teamUuid){
        //PUT peut ne pas fonctionner à cause d'un bug de l'API
        // Si problème utiliser POST en précisant l'id dans body
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.put(url+teamUuid+vehicleId, request);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public static boolean delVehicle(String vehicleId, String teamUuid){
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.delete(url+teamUuid+vehicleId);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static VehicleObject addVehicle(Object request, String teamUuid){
        RestTemplate restTemplate = new RestTemplate();
        VehicleObject vehicle = null;
        try{
            vehicle = restTemplate.postForObject(url+teamUuid, request, VehicleObject.class);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
        }
        return vehicle;
    }
}
